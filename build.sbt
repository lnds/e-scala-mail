organization := "org.akarru"

name := """e-scala-mail"""

version := "0.1.2"

description := "deliver electronic email with scala using fancy DSL"


libraryDependencies ++= Seq (
  "javax.mail" % "mail" % "1.4.7",
  "javax.activation"  % "activation"  % "1.1.1"
)

licenses := Seq(
  ("MIT", url(s"https://github.com/lnds/${name.value}/blob/${version.value}/LICENSE"))
)

homepage := Some(url(s"https://github.com/lnds/${name.value}/#readme"))

scalaVersion := "2.11.1"
