package com.example.mp;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.util.Iterator;

public class MaxTemperatureJob {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            BasicConfigurator.configure();
            args = new String[]{"E:\\hadoop\\doc\\hadoop-book-4e\\input\\ncdc\\sample.txt", "E:\\hadoop\\data\\maxT_2"};
        }

        if (args.length != 2) {
            System.err.println("Usage: MaxTemperature <input path> <output path>");
            System.exit(-1);
        }

        Job job = Job.getInstance();
        job.setJarByClass(MaxTemperatureJob.class);
        job.setJobName("Max temperature");
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        job.setMapperClass(MaxTemperatureMapper.class);
        job.setReducerClass(MaxTemperatureReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}

class MaxTemperatureMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private static final int MISSING = 9999;

    MaxTemperatureMapper() {
    }

    public void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String year = line.substring(15, 19);
        int airTemperature;
        if (line.charAt(87) == '+') {
            airTemperature = Integer.parseInt(line.substring(88, 92));
        } else {
            airTemperature = Integer.parseInt(line.substring(87, 92));
        }

        String quality = line.substring(92, 93);
        if (airTemperature != 9999 && quality.matches("[01459]")) {
            context.write(new Text(year), new IntWritable(airTemperature));
        }

    }
}

class MaxTemperatureReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    MaxTemperatureReducer() {
    }

    public void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
        int maxValue = -2147483648;

        IntWritable value;
        for (Iterator var5 = values.iterator(); var5.hasNext(); maxValue = Math.max(maxValue, value.get())) {
            value = (IntWritable) var5.next();
        }

        context.write(key, new IntWritable(maxValue));
    }
}

