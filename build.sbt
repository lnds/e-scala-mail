organization := "org.akarru"

name := """tools.mail"""

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.1"

scalacOptions ++= Seq("-feature")

libraryDependencies ++= Seq (
	"org.apache.commons" % "commons-email" % "1.4"
)

