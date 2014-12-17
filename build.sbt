
name := "free-logging"

organization := "com.timperrett"

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.0.4",
  "ch.qos.logback" % "logback-classic" % "1.1.2")

resolvers += "Sonatype OSS Snapshots" at
  "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies += "com.storm-enroute" %% "scalameter" % "0.6"

testFrameworks += new TestFramework(
  "org.scalameter.ScalaMeterFramework")

logBuffered := false

scalacOptions ++= Seq(
  "-feature", 
  "-language:postfixOps", 
  "-language:implicitConversions")

scalaVersion := "2.11.4"
