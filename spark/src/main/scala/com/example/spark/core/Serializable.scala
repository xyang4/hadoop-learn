package com.example.spark.core

import com.example.spark.common.CommonConfig
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 序列化测试 & RDD 函数传递
  */

object Serializable extends CommonConfig {
  def main(args: Array[String]): Unit = {

    val config: SparkConf = new SparkConf().setMaster(SPARK_MASTER).setAppName("Serializable")
    val sc = new SparkContext(config)

    val rdd: RDD[String] = sc.parallelize(Array("hadoop", "spark", "hive", "zk"))

    val search = new Search("h")

    // search 的 isMatch 方法要传给 executor，所以 Search 要可以序列化
    val match1: RDD[String] = search.matchByFun(rdd)

    match1.collect().foreach(println)

    // 匿名方法中包含 query, Search 要可以序列化
    val match2: RDD[String] = search.matchByAnonFun(rdd)

    match2.collect().foreach(println)
  }


}

/**
  * 分区
  */
object RDDTest extends CommonConfig {

  def main(args: Array[String]): Unit = {
    val config: SparkConf = new SparkConf().setMaster(SPARK_MASTER).setAppName("RDD")
    val sc = new SparkContext(config)

    val listRDD: RDD[Int] = sc.makeRDD(List(1, 2, 3, 2, 9, 6, 1))

    val distinctRDD: RDD[Int] = listRDD.distinct(2)
    distinctRDD.foreach(println)
    distinctRDD.saveAsTextFile(OUTPUT_BASE_DIR + "distinct")

    sc.stop()
  }
}

/**
  * 演示函数传递
  *
  * @param query
  */
class Search(query: String) extends java.io.Serializable {
  def matchByFun(rdd: RDD[String]): RDD[String] = {
    // filter内是成员方法
    rdd.filter(isMatch)
  }

  def isMatch(s: String): Boolean = {
    s.contains(query)
  }

  def matchByAnonFun(rdd: RDD[String]): RDD[String] = {
    // filter内是匿名函数，与对象无关，但query与对象有关
    rdd.filter(x => x.contains(query))
  }

}


