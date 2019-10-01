package com.example.spark.spark.core

import com.example.spark.spark.common.CommonConfig
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 3种缓存方式
  */
object Cache extends CommonConfig {
  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setMaster(SPARK_MASTER).setAppName("Cache")
    val sc = new SparkContext(sparkConf)

    val listRDD = sc.makeRDD(List(1, 2, 3))

    // 1 cache() 和 persist() 默认保存在内存中
    val noCache = listRDD.map(_.toString + System.currentTimeMillis())
    noCache.collect().foreach(a => println(a))
    noCache.collect().foreach(a => println(a)) // 每次执行都是从头开始，所以时间戳不同
    println("NoCache ===>", listRDD.toDebugString)

    // 2 cache // 从cache开始，时间戳相同
    val cache = listRDD.map(_.toString + System.currentTimeMillis()).cache()
    cache.collect().foreach(a => println(a))
    cache.collect().foreach(a => println(a))
    println("Cache ===>", listRDD.toDebugString)

    // checkpoint(),会将指定结果保存在指定目录
    // 设定检查点点保存目录
    //    (8) ShuffledRDD[4] at reduceByKey at Cache.scala:37 []
    //      +-(8) MapPartitionsRDD[3] at map at Cache.scala:33 []
    //         |  ReliableCheckpointRDD[5] at foreach at Cache.scala:40 []
    sc.setCheckpointDir(OUTPUT_BASE_DIR + "cache/cp")

    val mapRDD: RDD[(Int, Int)] = listRDD.map((_, 1))

    mapRDD.checkpoint()

    val reduceRDD: RDD[(Int, Int)] = mapRDD.reduceByKey(_ + _)

    // 执行行动操作后checkpoint才生效
    reduceRDD.foreach(println)
    println("checkPoint ===>", reduceRDD.toDebugString)

    sc.stop()
  }
}
