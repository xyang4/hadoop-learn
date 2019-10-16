package com.example.kafka

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.joda.time.LocalDate

/**
  * 生产者
  */
object Producer extends App {

  val props = new Properties()
  props.put("bootstrap.servers", KafkaConfig.BROKERS)
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("group.id", "scala_g")

  val producer = new KafkaProducer[String, String](props)
  producer.send(new ProducerRecord("scala-topic", "key", "Begin ... " + new LocalDate().toDate))

  for (i <- 1 to 50) {
    producer.send(new ProducerRecord[String, String]("scala-topic", "key", "Hi~,_" + i))
  }
  producer.send(new ProducerRecord("scala-topic", "key", "Over! " + new LocalDate().toDate))
  producer.close()
  println("Success!")

}
