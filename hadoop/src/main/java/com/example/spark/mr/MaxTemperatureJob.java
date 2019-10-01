package com.example.spark.mr;

import com.example.spark.util.LocalDebugUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * 获取历史年份中每年全球气温最高记录
 */
@Slf4j
public class MaxTemperatureJob {
    //    private static final String jobName = "ncdc/micro";
    private static final String jobName = "ncdc/all";

    public static void main(String[] args) throws Exception {
        // 1 handle params
        args = LocalDebugUtils.initInput(args, jobName);

        if (args.length != 2) {
            System.err.println("Usage: MaxTemperature <input path> <output path>");
            System.exit(-1);
        }
        // 2 job setting & config
        Job job = Job.getInstance();
        job.setJarByClass(MaxTemperatureJob.class);
        job.setJobName(jobName);

        job.setMapperClass(MaxTemperatureMapper.class);
        // 2.1 使用 combiner 进行优化
        // job.setCombinerClass(MaxTemperatureReducer.class);
        job.setReducerClass(MaxTemperatureReducer.class);

        //  2.2 compress 1 输入自动断定使用何种解压方式,对输出进行压缩

        // FileOutputFormat.setCompressOutput(job, true);
        // FileOutputFormat.setOutputCompressorClass(job, GzipCodec.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // 3 input & output setting
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileInputFormat.setInputDirRecursive(job, true); // 递归目录下的所有文件
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        // 4 commit
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}

class MaxTemperatureMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private static final int MISSING = 9999;

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String year = line.substring(15, 19);
        int airTemperature;
        if (line.charAt(87) == '+') {
            airTemperature = Integer.parseInt(line.substring(88, 92));
        } else {
            airTemperature = Integer.parseInt(line.substring(87, 92));
        }

        String quality = line.substring(92, 93);
        if (airTemperature != MISSING && quality.matches("[01459]")) {
            context.write(new Text(year), new IntWritable(airTemperature));
        }

    }
}

class MaxTemperatureReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        int maxValue = Integer.MIN_VALUE;
        for (IntWritable value : values) {
            maxValue = Math.max(maxValue, value.get());
        }
        context.write(key, new IntWritable(maxValue));
    }
}

