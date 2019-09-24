package com.example.scala

/**
  * 方法声明： def functionName ([参数列表]) : [return type]
  */
package method {

  /**
    *
    * 方法及函数：
    * 1 方法声明
    * 2 方法定义
    * 3 方法调用
    *
    *
    *
    *
    *
    *
    */
  class Test {
    val fun = (a: Int, b: Int) => a + b

    def mAdd(a: Int, b: Int) = a + b

    /**
      * 默认参数
      */
    def mMin(a: Int, b: Int = 0): Int = {
      return Math.min(a, b)
    }
  }

  object Method {

    var factor = 0.75
    var anonymousFuncInc = (x: Int) => x + factor
    var userDir = () => {
      System.getProperty("user.dir")
    }

    /**
      * 有返回值的方法
      *
      * @param a
      * @param b
      * @return
      */
    def addInt(a: Int, b: Int): Int = {
      var sum: Int = 0
      sum = a + b

      return sum
    }

    /*
    函数调用案例：
        函数传名调用(Call-by-Name)
        指定函数参数名
        函数 - 可变参数
        递归函数
        默认参数值
        高阶函数
        内嵌函数
        匿名函数
        偏应用函数
        函数柯里化(Function Currying)
     */
    def main(args: Array[String]): Unit = {
      say("Tom")
      //      函数&方法定义
      val t = new Test
      println(t.fun(1, 2) + " " + t.mAdd(a = 2, b = 10) + " " + t.mMin(2, 10))
      //
      println(delayed(System.nanoTime()))
      //       可变参数
      printStrings("a", "b", "c")
      //      局部函数/内嵌函数：定义在函数内的函数
      factorial(10)
      //      递归函数
      for (i <- 1 to 10) {
        println(i + " 的阶乘为: = " + recursion(i))
      }
      // 高阶函数： 操作其他函数的函数。
      println(apply(layout, 10))

      // 匿名函数
      println(anonymousFuncInc(10))
      println("user dir:" + userDir())

      // fun currying
      var str1: String = "Hello"
      var str2 = "World"
      println("str1 + str2 = " + funCurrying(str1)(str2))
    }

    // 无返回值的方法
    def say(word: String): Unit = {
      println("Hello ".concat(word))
    }

    //
    def delayed(t: Long): Long = {
      printf("P[%s] Time[%s] \n", t, System.nanoTime())
      /*return */ t
    }

    // 无返回值的可变参数
    def printStrings(args: String*) = {
      var i: Int = 0;
      for (arg <- args) {
        println("Arg value[" + i + "] = " + arg);
        i = i + 1;
      }
    }

    /**
      * 局部函数,如果内部函数定义在最后需要注意下返回值
      *
      * @param i
      * @return
      */
    def factorial(i: Int): Int = {

      def fact(i: Int, accumulator: Int): Int = {
        if (i <= 1)
          accumulator
        else
          fact(i - 1, i * accumulator)
      }

      fact(i, 1)
    }

    def recursion(n: BigInt): BigInt = {
      if (n <= 1)
        1
      else
        n * recursion(n - 1)
    }

    // 函数 f 和值 v 作为参数，而函数 f 又调用了参数 v
    def apply(f: Int => String, v: Int) = f(v)

    def layout[A](x: A) = "[" + x.toString() + "]"

    def funCurrying(s1: String)(s2: String) = s1 + s2
  }

}
