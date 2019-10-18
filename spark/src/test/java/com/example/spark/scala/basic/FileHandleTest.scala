package com.example.spark.scala.basic

import java.io.{File, PrintWriter}

import scala.io.{Source, StdIn}

/**
  * 文件操作相关
  */
object FileHandleTest {
  def readFromStd(): Unit = {
    println("Text: " + StdIn.readLine())
  }

  def main(args: Array[String]): Unit = {
    readFromFile()
  }

  def readFromFile(): Unit = {
    //    Source.fromFile("test.txt").foreach(println)
    val file = new File("E:\\hadoop\\code\\hadoop-learn\\spark\\src\\test\\resources\\test.txt")
    Source.fromFile(file, "utf-8").foreach(print)
    val writer = new PrintWriter(file)
    writer.append("Read Over!!!")
    writer.close()

  }
}
