ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.1.2"

lazy val root = (project in file("."))
  .settings(
    name := "PhotoRecognizer"
  )

libraryDependencies += "com.typesafe" % "config" % "1.4.2"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.11" % Test