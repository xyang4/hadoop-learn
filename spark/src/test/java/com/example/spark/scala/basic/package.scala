package com.example.spark.scala

package object grammar {

  // case 测试

  def main(args: Array[String]): Unit = {
    // 简单匹配
    simpleTest
  }

  def simpleTest(): Unit = {
    val bools = List(true, false)
    for (bool <- bools) {
      bool match {
        case true => println("heads")
        case false => println("tails")
        case _ => println("something other than heads or tails (yikes!)")
      }
    }
  }

}
