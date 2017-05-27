name := """scoutplay2"""
organization := "vbashur"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.11"

libraryDependencies += filters
libraryDependencies += specs2 % Test
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0" % Test
libraryDependencies += "org.reactivemongo" % "play2-reactivemongo_2.11" % "0.12.3"


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "vbashur.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "vbashur.binders._"
