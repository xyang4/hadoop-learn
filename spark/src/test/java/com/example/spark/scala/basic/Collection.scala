package com.example.spark.scala.basic

object Collection {
  def defina(): Unit = {
    // 定义整型 List
    val lsit = List(1, 2, 3, 4)

    // 定义 Set
    val set = Set(1, 3, 5, 7)

    // 定义 Map
    val map = Map("one" -> 1, "two" -> 2, "three" -> 3)

    // 创建两个不同类型元素的元组
    val tuple = (10, "Runoob")

    // 定义 Option : Option[T] 表示有可能包含值的容器，也可能不包含值。
    val option: Option[Int] = Some(5)
  }

}
