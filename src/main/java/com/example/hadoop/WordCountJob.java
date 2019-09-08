package com.example.hadoop;

import org.apache.hadoop.conf.Configuration;
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
 * WordCount
 *
 * @author xYang
 */
public class WordCountJob {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
//        conf.set("mapreduce.framework.name", "yarn");
//        conf.set("yarn.resourcemanager.hostname", "node1");

        Job job = Job.getInstance(conf, "wc");

        job.setJarByClass(WordCountJob.class);

        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

//        FileInputFormat.setInputPaths(job, "hdfs://node1:9000/wordcount/input/");
//        FileOutputFormat.setOutputPath(job, new Path("hdfs://node1:9000/wordcount/output/"));
        FileInputFormat.setInputPaths(job, "D:/temp/hadoop/input");
        FileOutputFormat.setOutputPath(job, new Path("D:/temp/hadoop/output"));
        job.waitForCompletion(true);
    }
}

class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private Text text = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] words = line.split(" ");
        for (String item : words) {
            text.set(item);
            context.write(text, new IntWritable(1));
        }
    }
}

class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException,
            InterruptedException {
        int count = 0;
        for (IntWritable item : values) {
            count += item.get();
        }
        context.write(key, new IntWritable(count));
    }

}
