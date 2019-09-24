package com.example.spark

class CommonConfig {
  val SPARK_HOME = "/usr/local/spark"
  val SCALA_HOME = "/usr/local/scala"
  val OUTPUT_BASE_DIR = "data/output/spark/"
  val INPUT_BASE_DIR = "data/input/spark/"
  /**
    * spark master 模式：
    * Local模式就是运行在一台计算机上的模式，通常就是用于在本机上练手和测试。它可以通过以下集中方式设置master。
    * local: 所有计算都运行在一个线程当中，没有任何并行计算，通常我们在本机执行一些测试代码，或者练手，就用这种模式。
    * local[K]: 指定使用几个线程来运行计算，比如local[4]就是运行4个worker线程。通常我们的cpu有几个core，就指定几个线程，最大化利用cpu的计算能力
    * local[*]: 这种模式直接帮你按照cpu最多cores来设置线程数了。
    */
  val SPARK_MASTER = "local"
}
