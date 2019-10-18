package com.example.spark.scala.features

/**
  * 提取器 Extractor（一个带有unapply方法的对象）：从传递给它的对象中提取出构造该对象的参数。
  */
object ExtractorTest {
  def main(args: Array[String]): Unit = {
    println("Apply 方法 : " + apply("Zara", "gmail.com"))
    println("Unapply 方法 : " + unapply("Zara@gmail.com"))
    println("Unapply 方法 : " + unapply("Zara Ali"))

    // 有apply的对象可以自动创建对象
    println(ExtractorTest("xyang", "csfjn.com"))

    // match 时自动调 unapply 方法
    ExtractorTest(20) match {
      case ExtractorTest(num) => println("是" + num + "的2倍")
      case _ => println("无法计算")

    }
  }


  /**
    * 注入方法 (可选),通过 apply 方法我们无需使用 new 操作就可以创建对象
    */
  def apply(user: String, domain: String) = {
    user + "@" + domain
  }

  /**
    * 提取方法（必选）
    */
  def unapply(str: String): Option[(String, String)] = {
    val parts = str split "@"
    if (parts.length == 2) {
      Some(parts(0), parts(1))
    } else {
      None
    }
  }

  //提取器使用模式匹配
  def apply(x: Int) = x * 2

  def unapply(x: Int): Option[Int] = {
    println("invoke...")
    if (x % 2 == 0) Some(x / 2) else None
  }
}
