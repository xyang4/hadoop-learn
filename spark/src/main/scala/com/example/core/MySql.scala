package com.example.core

;

import java.sql.DriverManager

import com.example.CommonConfig
import org.apache.spark.rdd.JdbcRDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * mysql 查询与写入
  */
object Mysql extends CommonConfig {

  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Mysql")
    val sc = new SparkContext(sparkConf)

    val driver = "com.mysql.cj.jdbc.Driver"
    val url = "jdbc:mysql://192.168.10.84:23307/plana_test_beta"
    val userName = "xz_dev"
    val passWd = "xz_739"

    // mysql 查询

    val jdbcRDD = new JdbcRDD(sc, () => {
      // 获取数据库连接对象
      Class.forName(driver)
      DriverManager.getConnection(url, userName, passWd)
    },
      "select trade_code,third_trade_no,user_id,timestamp from hc_api_request_record where exec_consuming>=? and exec_consuming<=?",
      2000, 3000, 3,
      rs => {
        printf("trade_code[%s] third_trade_no[%s] user_id[%s] timestamp[%s]\n", rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4))
      }
    )

    //打印最后结果
    jdbcRDD.collect()
    //    jdbcRDD.saveAsTextFile(OUTPUT_BASE_DIR + "mysql")
    // mysql 写入
    /*val dataRDD = sc.makeRDD(List(("0:0:0:0:0:0:0:1"), ("0:0:0:0:0:0:0:1")))


    // 方式1，效率不高
    //    dataRDD.foreach {
    //      case (ip) => {
    //        Class.forName(driver)
    //        val connection = DriverManager.getConnection(url, userName, passWd)
    //        val sql = "insert into log(ip) values (?)"
    //        val statement: PreparedStatement = connection.prepareStatement(sql)
    //        statement.setString(1, ip)
    //        statement.executeUpdate()
    //        statement.close()
    //        connection.close()
    //      }
    //    }

    // 方式2，以分区为单位，一个分区连接一次
    dataRDD.foreachPartition(datas => {
      Class.forName(driver)
      val connection = DriverManager.getConnection(url, userName, passWd)
      datas.foreach {
        case (ip) => {
          val sql = "insert into log(ip) values (?)"
          val statement: PreparedStatement = connection.prepareStatement(sql)
          statement.setString(1, ip)
          statement.executeUpdate()
          statement.close()
        }
      }
      connection.close()
    })
*/
    sc.stop()
  }
}
