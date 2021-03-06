name := """line_bot"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

import com.github.play2war.plugin._

Play2WarPlugin.play2WarSettings

Play2WarKeys.servletVersion := "3.1"

libraryDependencies ++= Seq(
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "mysql" % "mysql-connector-java" % "5.1.34",
  "com.typesafe.play" %% "play-slick" % "2.0.2",
  "com.typesafe.play" %% "play-slick-evolutions" % "2.0.0",
  "com.typesafe.slick" %% "slick" % "3.1.1",
  "org.mariadb.jdbc" % "mariadb-java-client" % "1.4.4",
  "com.adrianhurt" %% "play-bootstrap" % "1.1-P25-B4"
)

fork in run := false