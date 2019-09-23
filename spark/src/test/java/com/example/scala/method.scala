package com.example.scala

/**
  * 方法声明： def functionName ([参数列表]) : [return type]
  */
package method {

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

  object Add {

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

    def factorial(i: Int): Int = {
      fact(i, 1)

      def fact(i: Int, accumulator: Int): Int = {
        if (i <= 1)
          accumulator
        else
          fact(i - 1, i * accumulator)
      }
    }
  }

}
