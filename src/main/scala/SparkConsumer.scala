package com.tamisin.spark.streaming

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe



object SparkConsumer {
  def main(args : Array[String]) {

      val kafkaParams = Map[String, Object](
       "bootstrap.servers" -> "localhost:9092",
       "key.deserializer" -> classOf[StringDeserializer],
       "value.deserializer" -> classOf[StringDeserializer],
       "group.id" -> "Spark",
       "auto.offset.reset" -> "latest",
       "enable.auto.commit" -> (false: java.lang.Boolean)
      )

      val topics = Array("KAFKA_TWEETS")
      val stream = KafkaUtils.createDirectStream[String, String](streamingContext,PreferConsistent,Subscribe[String, String](topics, kafkaParams))

      //stream.map(record => (record.key, record.value)) 
      val wordCounts = stream.map(x => (x, 1L))
                             .reduceByKeyAndWindow(_ + _, _ - _, Minutes(10), Seconds(2), 2)
      wordCounts.print()
      }
}