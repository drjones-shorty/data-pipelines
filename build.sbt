name := "TwitterProducer"

version := "1.0"

scalaVersion := "2.12.1"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "org.twitter4j" % "twitter4j" % "4.0.6"
libraryDependencies += "org.twitter4j" % "twitter4j-stream" % "4.0.6"
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "0.10.2.1"
libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.2.0"
libraryDependencies += "org.apache.spark" % "spark-streaming_2.11" % "2.2.0" % "provided"
libraryDependencies += "org.apache.spark" % "spark-streaming-kafka-0-10_2.11" % "2.2.0"

// for debugging sbt problems
logLevel := Level.Debug

scalacOptions += "-deprecation"