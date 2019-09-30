package com.example.scala.clazz

/**
  * Trait   似于 Java 的接口，用于扩展类
  */
package traitTest {


  trait HairColor

  trait EyesColor

  trait SkinColor

  class Man extends HairColor with EyesColor with SkinColor

  trait Equal {
    def isEqual(x: Any): Boolean

    def isNotEqual(x: Any): Boolean = !isEqual(x)
  }

  class Point(xc: Int, yc: Int) extends Equal {
    var x: Int = xc
    var y: Int = yc

    override def isEqual(obj: Any) = obj.isInstanceOf[Point] && obj.asInstanceOf[Point].x == x
  }

  object Test extends App {
    val p1 = new Point(2, 3)
    val p2 = new Point(2, 4)
    val p3 = new Point(3, 3)

    println(p1.isNotEqual(p2))
    println(p1.isNotEqual(p3))
    println(p1.isNotEqual(2))
  }

  object EnvTest extends App {
    println(System.getenv("OS").toLowerCase().contains("window"))
  }

}
