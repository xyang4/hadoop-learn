package com.example.spark.scala.clazz

trait HairColor

trait EyesColor

trait SkinColor

class ManyColor extends HairColor with EyesColor with SkinColor

trait Equal {
  def isEqual(x: Any): Boolean

  def isNotEqual(x: Any): Boolean = !isEqual(x)
}

class SonOfEqual(xc: Int, yc: Int) extends Equal {
  var x: Int = xc
  var y: Int = yc

  override def isEqual(obj: Any) = obj.isInstanceOf[Point] && obj.asInstanceOf[Point].x == x
}

object Trait extends App {
  val p1 = new SonOfEqual(2, 3)
  val p2 = new SonOfEqual(2, 4)
  val p3 = new SonOfEqual(3, 3)

  // todo do something!!!
  println(p1.isNotEqual(p2))
  println(p2.isNotEqual(p3))
}

object EnvTest extends App {
  //  println(System.getenv())
  println(System.getProperty("os.name").toLowerCase().contains("window"))
}
