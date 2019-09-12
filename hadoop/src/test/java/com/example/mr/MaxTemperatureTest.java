package com.example.mr;

import com.example.CommonTest;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

/**
 * @author xYang
 * @date 2019/9/12 0012
 * @purchase // 一句话说明
 */
public class MaxTemperatureTest extends CommonTest {

    @Test
    public void processesValidRecord() throws IOException {
        Text value = new Text("0043011990999991950051518004+68750+023550FM-12+0382" +
                // Year ^^^^
                "99999V0203201N00261220001CN9999999N9-00111+99999999999");
        // Temperature ^^^^^
        new MapDriver<LongWritable, Text, Text, IntWritable>()
                .withMapper(new MaxTemperatureMapper())
                .withInput(new LongWritable(0), value) // map 阶段不处理 key
                .withOutput(new Text("1950"), new IntWritable(-11))
                .runTest();
    }

    // ^^ MaxTemperatureMapperTestV1
    @Ignore // since we are showing a failing test in the book
// vv MaxTemperatureMapperTestV1Missing
    @Test
    public void ignoresMissingTemperatureRecord() throws IOException {
        Text value = new Text("0043011990999991950051518004+68750+023550FM-12+0382" +
                // Year ^^^^
                "99999V0203201N00261220001CN9999999N9+99991+99999999999");
        // Temperature ^^^^^
        new MapDriver<LongWritable, Text, Text, IntWritable>()
                .withMapper(new MaxTemperatureMapper())
                .withInput(new LongWritable(0), value)
                .runTest();
    }

    // ^^ MaxTemperatureMapperTestV1Missing
    @Test
    public void processesMalformedTemperatureRecord() throws IOException,
            InterruptedException {
        Text value = new Text("0335999999433181957042302005+37950+139117SAO  +0004" +
                // Year ^^^^
                "RJSN V02011359003150070356999999433201957010100005+353");
        // Temperature ^^^^^
        new MapDriver<LongWritable, Text, Text, IntWritable>()
                .withMapper(new MaxTemperatureMapper())
                .withInput(new LongWritable(0), value)
                .withOutput(new Text("1957"), new IntWritable(1957))
                .runTest();
    }
}
