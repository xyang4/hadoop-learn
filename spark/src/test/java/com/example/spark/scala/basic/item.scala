package com.example.spark.scala.basic

object ForItem {
  def main(args: Array[String]): Unit = {

    for (x <- 1 to 10) {
      print(x + " ")
    }
    println()

    var a: Int = 1
    for (a <- 1 to 3; b <- 1 to 3) {
      println("Value of a: " + a);
      println("Value of b: " + b);
    }

    for (v <- Array.tabulate(10)(v => v * v).toList; if v > 2; if v <= 4) {
      println(v)
    }

    val numList = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    // for 循环
    var retVal = for {a <- numList
                      if a != 3; if a < 8
    } yield a
    for (v <- retVal) {
      println("Value of a: " + a);
    }
  }
}
