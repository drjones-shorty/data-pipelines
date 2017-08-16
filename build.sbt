name := "TwitterProducer"

version := "1.0"

scalaVersion := "2.12.1"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "org.twitter4j" % "twitter4j" % "4.0.6"
libraryDependencies += "org.twitter4j" % "twitter4j-stream" % "4.0.6"
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "0.10.2.1"

// for debugging sbt problems
logLevel := Level.Debug

scalacOptions += "-deprecation"