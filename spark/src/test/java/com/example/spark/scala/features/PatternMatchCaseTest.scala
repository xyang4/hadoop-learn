package com.example.spark.scala.features

/**
  * 模式匹配
  */
object PatternMatchCaseTest {
  def main(args: Array[String]): Unit = {
    println(matchStr(2))
    caseClazzTest()
  }

  def matchStr(x: Int): String = x match {
    case 1 => "one"
    case 2 => "two"
    case _ => "many"
  }

  /**
    * 使用样例类
    */
  def caseClazzTest(): Unit = {
    var charlie = new Person("Charlie", 32)
    for (person <- List(new Person("Alice", 25), new Person("Bob", 32), charlie)) {
      person match {
        case Person("Alice", 25) => println("Hi Alice!")
        case Person("Bob", 32) => println("Hi Bob!")
        case Person(name, age) => {
          printf("name[%s] age[%d]\n", name, age)
        }
      }
    }
  }

  case class Person(name: String, age: Int)

}
