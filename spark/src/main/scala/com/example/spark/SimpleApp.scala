package com.example.spark

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object SimpleAppWithScala extends CommonConfig {
  /**
    * Hello World
    *
    * @param args
    */
  def main(args: Array[String]) {

    val logFile = SPARK_HOME + "/README.md" // Should be some file on your system
    val spark = SparkSession.builder.appName("Simple Application").config("spark.master", "local").getOrCreate()
    val logData = spark.read.textFile(logFile).cache()
    val numAs = logData.filter(line => line.contains("a")).count()
    val numBs = logData.filter(line => line.contains("b")).count()
    println(s"Lines with a: $numAs, Lines with b: $numBs")
    spark.stop()
  }
}

import org.apache.spark.SparkConf

/**
  * 单词统计
  */
object WordCount extends CommonConfig {

  def main(args: Array[String]): Unit = {
    val sparkConfig: SparkConf = new SparkConf().setMaster(SPARK_MASTER).setAppName("WordCount")
    val sc: SparkContext = SparkContext.getOrCreate(sparkConfig)

    sc.textFile(INPUT_BASE_DIR + "word/wordCount.txt")
      .flatMap(_.split(" "))
      .map((_, 1))
      .reduceByKey(_ + _)
      .collect()
      .foreach(println)

    sc.stop()
  }
}
