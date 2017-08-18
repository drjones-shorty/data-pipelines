package com.tamisin.kafka.producers

import java.util._

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import twitter4j._

/*
 * Twitter & Kafka Configuration
 */
 class TwitterControl (val pTopic: String, val pBrokers: String){
  val configBuilder = new twitter4j.conf.ConfigurationBuilder()
    .setOAuthConsumerKey("vpVMwUe9nH8aoPvZdLqZmV63Q")
    .setOAuthConsumerSecret("D7Qgdn4KWWQFuMcpZkyzcuOIthB4s0sNcU2Z1H2mrMCWAD02IE")
    .setOAuthAccessToken("740030951082754048-2ByqOsTpWf4xZbZVm1UKmlh5VC9BvGb")
    .setOAuthAccessTokenSecret("6pLqzZEeljQXr9YDxyxKcKpmGmPvrUGKwuPXmyAihG2px")
    .build()

  val props = new Properties()
  props.put("bootstrap.servers", pBrokers)
  props.put("client.id", "TwitterAccount")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  /* 
   * Listener Behavior for incoming Tweets
   *  @todo fill in other behaviours
   * */
  def listener = new StatusListener() {
    def onStatus(status: Status) {
      println("Sending " + status.getText + " to " + pTopic)
      val producer = new KafkaProducer[String, String](props)
      val data = new ProducerRecord[String, String](pTopic, status.getText)
      producer.send(data)
      producer.close()
    }
    def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {}
    def onTrackLimitationNotice(numberOfLimitedStatuses: Int) {}
    def onException(ex: Exception) { ex.printStackTrace }
    def onScrubGeo(arg0: Long, arg1: Long) {}
    def onStallWarning(warning: StallWarning) {}
  }
}

/*
 * Listen for Twitter Stream and send messages to Kafka Broker
 *  @param filter a term to use for filtering (e.g. #KAFKA)
 *  @param topic the target kafka topic (e.g. KAFKATWEETS)
 *  @param broker the kafka broker address (e.g. localhost:9092)
 */
object TwitterProducer {
  def main(args: Array[String]) {
    val config = new TwitterControl(args(1),args(2))
    val twitterStream = new TwitterStreamFactory(config.configBuilder).getInstance
    twitterStream.addListener(config.listener)
    // Limit Messages
    twitterStream.filter(new FilterQuery().track(args(0)))
  }
}

