package com.example.kafka

import java.time.Duration
import java.util.{Collections, Properties}

import org.apache.kafka.clients.consumer.KafkaConsumer


/**
  * 消费者
  */
object ComsumerExample extends App {

  val props = new Properties()
  props.put("bootstrap.servers", KafkaConfig.BROKERS)
  props.put("client.id", "ScalaProducerExample")
  props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("group.id", "scala_g")

  val consumer = new KafkaConsumer[String, String](props)
  consumer.subscribe(Collections.singleton("scala-topic"))
  while (true) {
    val records = consumer.poll(Duration.ofMillis(1000l * 1))
    records.records("scala-topic").forEach(println)
  }
  // consumer.close()
}
