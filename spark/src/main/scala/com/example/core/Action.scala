package com.example.core

import com.example.CommonConfig
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 算子： action
  * RDD 提供两种类型的操作：
  * 1 转换：从现有数据集创建新数据集。转换是惰性的，因为它们仅在动作需要将结果返回到驱动程序时才计算。
  * 2 行动：在对数据集运行计算后将值返回给驱动程序。
  *
  * RDD 的创建方式:
  * 1 由驱动程序中的集合对象通过并行化操作创建，
  * 2 从外部存储系统中数据集加载（如：共享文件系统、HDFS、HBase或者其他Hadoop支持的数据源）。
  */
object Action extends CommonConfig {
  def main(args: Array[String]): Unit = {
    val config: SparkConf = new SparkConf().setMaster(SPARK_MASTER).setAppName("Action")
    val sc = new SparkContext(config)

    val listRDD = sc.makeRDD(List(1, 3, 4, 5, 9, 8), 2)
    val pairRDD: RDD[(Int, String)] = sc.parallelize(Array((1, "aa"), (2, "bb"), (3, "cc"), (4, "dd")), 4)

    /**
      * reduce(func)：reduceByKey是转换算子，reduce是行动算子
      */

    //    println(listRDD.reduce(_ + _))

    /**
      * collect()
      * 收集返回数组
      */

    //    listRDD.collect()
    //      .foreach(println)

    /**
      * count()
      * 计数
      */

    //    println(listRDD.count())

    /**
      * first()
      * 返回第一个
      */

    printf("First[%s] Count[%d]", listRDD.first(), listRDD.count())

    /**
      * take(n)
      * 取前几个
      */

    //    listRDD.take(3).foreach(println)

    /**
      * takeOrdered(n)
      * 取排序后的前几个
      */

    //    listRDD.takeOrdered(3).foreach(println)

    /**
      * aggregate[U: ClassTag](zeroValue: U)(seqOp: (U, T) => U, combOp: (U, U) => U)
      * seqOp分区内，combOp分区间
      * aggregate分区间也加初始值，aggregateByKey不加
      */

    println("aggregate:", listRDD.aggregate(10)(_ + _, _ + _))

    /**
      * fold(zeroValue: T)(op: (T, T) => T)
      * aggregate 简化
      */

    println("fold:", listRDD.fold(10)(_ + _))

    /**
      * saveAsTextFile(...)
      * saveAsObjectFile(...)
      */

    //    listRDD.saveAsTextFile("data/output1")
    //    listRDD.saveAsObjectFile("data/output2")


    /**
      * countByKey(): Map[K, Long]
      * 生成的是字典
      */

    println("countByKey: ", pairRDD.countByKey())

    /*
      * foreach(func)
      */
    listRDD.foreach(println)

    sc.stop()
  }
}
