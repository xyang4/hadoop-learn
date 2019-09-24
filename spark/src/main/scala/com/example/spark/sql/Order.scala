package com.example.spark.sql

import com.example.spark.CommonConfig
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Dataset, SparkSession}

/**
  * 订单业务: 一个订单有多件商品，有一个订单日期
  */

object Order extends CommonConfig {

  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder.config(new SparkConf().setMaster(SPARK_MASTER).setAppName("Order")).getOrCreate()
    import spark.implicits._

    // date
    val tbDateRdd: RDD[String] = spark.sparkContext.textFile(INPUT_BASE_DIR + "order/tbDate.txt")
    val tbDateDS: Dataset[tbDate] = tbDateRdd.map(_.split(",")).map(
      attr => tbDate(attr(0), attr(1).trim().toInt, attr(2).trim().toInt, attr(3).trim().toInt, attr(4).trim().toInt,
        attr(5).trim().toInt, attr(6).trim().toInt, attr(7).trim().toInt, attr(8).trim().toInt, attr(9).trim().toInt)
    ).toDS
    tbDateDS.createOrReplaceTempView("tbDate")
    tbDateDS.show()
    // stock
    val tbStockRdd: RDD[String] = spark.sparkContext.textFile(INPUT_BASE_DIR + "order/tbStock.txt")
    val tbStockDS: Dataset[tbStock] = tbStockRdd.map(_.split(",")).map(attr => tbStock(attr(0), attr(1), attr(2))).toDS
    tbStockDS.createOrReplaceTempView("tbStock")
    tbStockDS.show()

    val tbStockDetailRdd: RDD[String] = spark.sparkContext.textFile(INPUT_BASE_DIR + "order/tbStockDetail.txt")
    val tbStockDetailDS: Dataset[tbStockDetail] = tbStockDetailRdd.map(_.split(",")).map(
      attr => tbStockDetail(attr(0), attr(1).trim().toInt, attr(2), attr(3).trim().toInt, attr(4).trim().toDouble, attr(5).trim().toDouble)
    ).toDS
    tbStockDetailDS.createOrReplaceTempView("tbStockDetail")
    tbStockDetailDS.show()

    /**
      * 案例一：计算所有订单中每年的销售单数、销售总额
      * 将3表join，按年分GROUP，求order的数量，计算amount的总和
      * +---------+------+-------+-----+---+-------+----+-------+------+---------+
      * |   dateId| years|theYear|month|day|weekday|week|quarter|period|halfMonth|
      * +---------+------+-------+-----+---+-------+----+-------+------+---------+
      * | 2003-1-1|200301|   2003|    1|  1|      3|   1|      1|     1|        1|
      * | 2003-1-2|200301|   2003|    1|  2|      4|   1|      1|     1|        1|
      * +---------+------+-------+-----+---+-------+----+-------+------+---------+
      */

    spark.sql("SELECT c.theYear, COUNT(DISTINCT a.orderNumber), SUM(b.amount) " +
      "FROM tbStock a " +
      "JOIN tbStockDetail b ON a.orderNumber = b.orderNumber " +
      "JOIN tbDate c ON a.dateId = c.dateId " +
      "GROUP BY c.theYear " +
      "ORDER BY c.theYear").show


    /**
      * 案例二：计算所有订单每年最大金额订单的销售额
      * 先求出每年每个订单销售额的表，再和时间表join，找出每年最大金额订单的销售额
      */

    spark.sql("SELECT theYear, MAX(c.SumOfAmount) AS SumOfAmount " +
      "FROM " +
      "(SELECT a.dateId, a.orderNumber, SUM(b.amount) AS SumOfAmount " +
      "FROM tbStock a " +
      "JOIN tbStockDetail b ON a.orderNumber = b.orderNumber " +
      "GROUP BY a.dateId, a.orderNumber) c " +
      "JOIN tbDate d ON c.dateId = d.dateId " +
      "GROUP BY theYear ORDER BY theYear").show

    /**
      * 案例三：计算所有订单中每年最畅销货品
      * +-------+--------------+-----------+
      * |theYear|        itemId|maxOfAmount|
      * +-------+--------------+-----------+
      * |   2003|FS527258160501|      466.0|
      * |   2004|FS527258160501|     1188.0|
      * |   2005|FS527258160501|      396.0|
      * +-------+--------------+-----------+
      */

    spark.sql("SELECT DISTINCT e.theYear, e.itemId, f.maxOfAmount " +
      "FROM " +
      "(SELECT c.theYear, b.itemId, SUM(b.amount) AS sumOfAmount " +
      "FROM tbStock a " +
      "JOIN tbStockDetail b ON a.orderNumber = b.orderNumber " +
      "JOIN tbDate c ON a.dateId = c.dateId " +
      "GROUP BY c.theYear, b.itemId ) e " +
      "JOIN " +
      "(SELECT d.theYear, MAX(d.sumOfAmount) AS maxOfAmount " +
      "FROM " +
      "(SELECT c.theYear, b.itemId, SUM(b.amount) AS sumOfAmount " +
      "FROM tbStock a " +
      "JOIN tbStockDetail b ON a.orderNumber = b.orderNumber " +
      "JOIN tbDate c ON a.dateId = c.dateId " +
      "GROUP BY c.theYear, b.itemId ) d " +
      "GROUP BY d.theYear ) f " +
      "ON e.theYear = f.theYear AND e.sumOfAmount = f.maxOfAmount " +
      "ORDER BY e.theYear").show

    spark.stop()
  }

  case class tbDate(dateId: String, years: Int, theYear: Int, month: Int, day: Int, weekday: Int, week: Int, quarter: Int, period: Int, halfMonth: Int) extends Serializable

  case class tbStock(orderNumber: String, locationId: String, dateId: String) extends Serializable

  case class tbStockDetail(orderNumber: String, rowNum: Int, itemId: String, number: Int, price: Double, amount: Double) extends Serializable

}