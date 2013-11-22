
name := "free-logging"

organization := "com.timperrett"

libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.0.4"

scalacOptions ++= Seq(
  "-feature", 
  "-language:postfixOps", 
  "-language:implicitConversions")

scalaVersion := "2.10.3"
