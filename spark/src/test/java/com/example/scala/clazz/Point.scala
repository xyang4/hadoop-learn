package com.example.scala.clazz

class Point(xc: Int, yc: Int) {
  var x: Int = xc
  var y: Int = yc

  def move(dx: Int, dy: Int) {
    printf("O(%d %d)\n", x, y);
    x = x + dx
    y = y + dy
    printf("T(%d %d)\n", x, y);
  }
}

class Location(xp: Int, yp: Int, zp: Int) extends Point(xc = 0, yc = 0) {
  var z: Int = zp

  def move(dx: Int, dy: Int, dz: Int) {
    printf("O(%d %d %d)\n", x, y, z);
    x = x + dx
    y = y + dy
    z = z + dz
    printf("T(%d %d %d)\n", x, y, z);
  }
}


class Person {
  var name = ""

  override def toString = getClass.getName + "[name=" + name + "]"
}

class Employee extends Person {
  var salary = 0.0

  override def toString: String = super.toString + "[salary=" + salary + "]"
}

class Marker private(color: String) {
  println("create:" + this)

  override def toString(): String = "颜色标记：" + color
}

object Marker {
  private val markers: Map[String, Marker] = Map(
    "red" -> new Marker("red"),
    "blue" -> new Marker("blue"),
    "green" -> new Marker("green")
  )

  def main(args: Array[String]): Unit = {
    println(Marker("red"))
    // 单例函数调用，省略了.(点)符号
    println(Marker getMarker "blue")
  }

  def getMarker(color: String) = {
    if (markers.contains(color)) markers(color) else null
  }

  def apply(color: String) = {
    if (markers.contains(color)) markers(color) else null
  }
}

object Test {
  def main(args: Array[String]): Unit = {

    val location: Location = new Location(0, 0, 0)
    location.move(15, 15)
    location.move(15, 45, 90)
  }
}

object T extends App {
  val fred = new Employee
  fred.name = "Fred"
  fred.salary = 50000
  println(fred)
}