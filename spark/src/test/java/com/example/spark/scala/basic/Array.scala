package com.example.spark.scala.basic

import scala.Array._

object Test {
  def main(args: Array[String]) {
    //      baseArrayTest
    //      multArrTest // multArrTest()
    // 3 array handle
    // concat Range 2 to 10
    //      concat(Array(1 to 5), Array(2 to 10)).foreach(println)

    listTest
  }

  def listTest(): Unit = {
    //      val strList: List[String] = List("Go", "Java", "Python", "Scala")
    //      val site = "Runoob" :: ("Google" :: ("Baidu" :: Nil))
    //      var empty: List[Nothing] = List()
    //
    //      println(Nil.size + " Empty List Size:" + empty.size)
    //      strList.map(v => v + "-" + v.length + " ").foreach(print)
    //      println(site)
    //
    //       连接
    //      println(List.concat(strList, site))
    //      println((strList ::: site).sortBy(x => x.length))

    // fill 指定重复数量列表
    //      println(List.fill(4)("Go"))

    // 通过给定的函数来创建列表。
    println("一维 : " + List.tabulate(6)(n => n * n)) // List(0, 1, 4, 9, 16, 25)
    println("二维 : " + List.tabulate(4, 5)(_ * _)) // List(List(0, 0, 0, 0, 0), List(0, 1, 2, 3, 4), List(0, 2, 4, 6, 8), List(0, 3, 6, 9, 12))
  }

  /**
    * 多维数组
    */
  def multArrTest(): Unit = {
    var myMatrix = ofDim[Int](3, 3)
    // create array
    for (i <- 0 to 2) {
      for (j <- 0 to 2) {
        myMatrix(i)(j) = j;
      }
    }

    // print array
    for (i <- 0 to 2) {
      for (j <- 0 to 2) {
        print(" " + myMatrix(i)(j));
      }
      println();
    }
  }

  def baseArrayTest = {
    var myList = Array(1.9, 2.9, 3.4, 3.5)

    myList.foreach(println)
    // 输出所有数组元素
    for (x <- myList) {
      println(x)
    }
    // 计算数组所有元素的总和
    var total = 0.0;
    for (i <- 0 to (myList.length - 1)) {
      total += myList(i);
    }
    println("总和为 " + total);

    // 查找数组中的最大元素
    var max = myList(0);
    for (i <- 1 to (myList.length - 1)) {
      if (myList(i) > max) max = myList(i);
    }
    println("最大值为 " + max);
  }

}
