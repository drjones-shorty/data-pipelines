package com.tamisin.spark.streaming

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe


object SparkConsumer {
  def main(args : Array[String]) {

      val kafkaParams = Map[String, Object](
      	  "bootstrap.servers" -> "localhost:9092",
      	  "key.deserializer" -> classOf[StringDeserializer],
      	  "value.deserializer" -> classOf[StringDeserializer],
      	  "group.id" -> "use_a_separate_group_id_for_each_stream",
      	  "auto.offset.reset" -> "latest",
      	  "enable.auto.commit" -> (false: java.lang.Boolean)
      )
   
        val conf = new SparkConf()
             .setMaster("local[*]")
             .setAppName("SparkCountingConsumer")
        val sc = new SparkContext(conf)
   	val streamingContext = new StreamingContext(conf, Seconds(1))


   	val topics = Array("KAFKA_TWEETS")
   	val stream = KafkaUtils.createDirectStream[String, String](
     	    streamingContext,
     	    PreferConsistent,
     	    Subscribe[String, String](topics, kafkaParams)
   	)
   	stream.map(record => (record.key, record.value))
  }
}