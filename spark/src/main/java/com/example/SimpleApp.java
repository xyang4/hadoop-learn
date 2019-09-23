package com.example;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;

/**
 * @author xYang
 * @date 2019/9/17 0017 15:43
 * @purchase //TODO 一句话说明
 */
public class SimpleApp {
    public static void main(String[] args) {
        String spark_home = "/usr/local/spark";
        String logFile = spark_home + "/README.md"; // Should be some file on your system
        SparkSession spark = SparkSession.builder().appName("Simple Application").master("local").getOrCreate();
        Dataset<String> logData = spark.read().textFile(logFile).cache();
        long numAs = logData.collectAsList().stream().filter(v -> v.contains("a")).count();
        long numBs = logData.collectAsList().stream().filter(v -> v.contains("b")).count();

        System.out.println("Lines with a: " + numAs + ", lines with b: " + numBs);
        spark.stop();
    }
}
