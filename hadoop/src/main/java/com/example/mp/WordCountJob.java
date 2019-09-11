package com.example.mp;

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
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.util.Iterator;

public class WordCountJob {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        if (args.length == 0) {
            BasicConfigurator.configure();
            args = new String[]{"data/input", "data/output/wc"};
        }

        if (args.length != 2) {
            System.err.println("Usage: MaxTemperature <input path> <output path>");
            System.exit(-1);
        }

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "wc");
        job.setJarByClass(WordCountJob.class);
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.setInputPaths(job, args[0]);
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        job.waitForCompletion(true);
    }
}

class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private Text text = new Text();

    WordCountMapper() {
    }

    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] words = line.split(" ");
        String[] var6 = words;
        int var7 = words.length;

        for (int var8 = 0; var8 < var7; ++var8) {
            String item = var6[var8];
            this.text.set(item);
            context.write(this.text, new IntWritable(1));
        }

    }
}

class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    WordCountReducer() {
    }

    protected void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
        int count = 0;

        IntWritable item;
        for (Iterator var5 = values.iterator(); var5.hasNext(); count += item.get()) {
            item = (IntWritable) var5.next();
        }

        context.write(key, new IntWritable(count));
    }
}
