name := "TwitterPipeline"

version := "0.1"

scalaVersion := "2.11.11"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "org.twitter4j" % "twitter4j" % "4.0.6"
libraryDependencies += "org.twitter4j" % "twitter4j-stream" % "4.0.6"
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "0.11.0.0"

libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.2.0"
libraryDependencies += "org.apache.spark" % "spark-streaming_2.11" % "2.2.0"
libraryDependencies += "org.apache.spark" % "spark-streaming-kafka-0-8_2.11" % "2.2.0"
libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.12" % "test"

// for debugging sbt problems
logLevel := Level.Debug

scalacOptions += "-deprecation"

