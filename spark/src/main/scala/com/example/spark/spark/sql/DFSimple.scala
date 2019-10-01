package com.example.spark.spark.sql

import com.example.spark.spark.common.CommonConfig
import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * DataFrame的创建和使用
  */

object DFSimple extends CommonConfig {

  def main(args: Array[String]): Unit = {

    val sparkConf: SparkConf = new SparkConf().setMaster(SPARK_MASTER).setAppName("DFTest")

    // 创建SparkSession
    val spark: SparkSession = SparkSession.builder.config(sparkConf).getOrCreate()

    // 从json中read得到的是DataFrame
    val frame: DataFrame = spark.read.json(INPUT_BASE_DIR + "user/user.json")

    // 编程方式1：直接使用DataFrame的方法
    frame.show()

    // 编程方式2：将DataFrame转换成一张user表，再执行sql
    frame.createOrReplaceTempView("user")
    spark.sql("select * from user").show()

    spark.stop()
  }
}
