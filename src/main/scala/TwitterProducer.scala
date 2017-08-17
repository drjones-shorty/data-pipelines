package com.tamisin
import twitter4j._
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object TwitterProducer {
  def main(args : Array[String]) {
    val twitterStream = new TwitterStreamFactory(TwitterControls.configBuilder).getInstance
    twitterStream.addListener(TwitterControls.listener)
    // Filter Messages
    twitterStream.filter(new FilterQuery().track("#KAFKA"))
  }
}

object TwitterControls {
    val configBuilder = new twitter4j.conf.ConfigurationBuilder()
      .setOAuthConsumerKey("vpVMwUe9nH8aoPvZdLqZmV63Q")
      .setOAuthConsumerSecret("D7Qgdn4KWWQFuMcpZkyzcuOIthB4s0sNcU2Z1H2mrMCWAD02IE")
      .setOAuthAccessToken("740030951082754048-2ByqOsTpWf4xZbZVm1UKmlh5VC9BvGb")
      .setOAuthAccessTokenSecret("6pLqzZEeljQXr9YDxyxKcKpmGmPvrUGKwuPXmyAihG2px")
      .build()

    val topic = "KAFKA_TWEETS"
    val brokers = "localhost:9092"
    val props = new Properties()
    props.put("bootstrap.servers", brokers)
    props.put("client.id", "TwitterAccount")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  
    def listener = new StatusListener() {
    	def onStatus(status: Status) { 
	    println(status.getText)
	    val producer = new KafkaProducer[String, String](props)
            val data = new ProducerRecord[String, String](topic, status.getText)
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
