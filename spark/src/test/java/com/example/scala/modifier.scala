package com.example.scala

/**
  * scala 中的修饰符比java中更严格：
  * 1 Protected 只允许保护成员在定义了该成员的的类的子类中被访问
  * 2 private   仅在包含了成员定义的类或对象内部可见，同样的规则还适用内部类。
  */
package modifier {

  class Outer {
    protected def a() {
      println("protected fun")
    }

    class Inner {
      def b(): Unit = {
        println("public fun")
      }

      private def c() {
        println("private fun")
      }

      class InnerMost {
        a() // 正确
        b()
        c()
      }

    }

  }

  object Outer {
    def main(args: Array[String]): Unit = {
      (new Outer).a()
      new navigation.Navigator().canAccess
    }
  }

  /**
    * 作用域保护
    */
  package navigation {

    private[modifier] class Navigator {
      private[this] var speed = 200

      def canAccess() {}

      protected[navigation] def useStarChart() {}

      class LegOfJourney {
        private[Navigator] val distance = 100
      }

    }

  }

  package launch {

    import com.example.scala.modifier.navigation._

    object Vehicle {
      private[launch] val guide = new Navigator
    }

  }

}
