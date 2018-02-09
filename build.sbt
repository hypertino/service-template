scalaVersion := "2.12.4"

lazy val `service-template` = project in file(".") enablePlugins (BuildInfoPlugin) settings (
  name := "service-template",
  version := "0.1-SNAPSHOT",
  organization := "com.hypertino",

  resolvers ++= Seq(
    Resolver.sonatypeRepo("public")
  ),

  libraryDependencies ++= Seq(
    "com.hypertino" %% "typesafe-config-binders" % "0.2.1",
    "com.hypertino" %% "service-config" % "0.2.8",
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2",
    "net.logstash.logback" % "logstash-logback-encoder" % "4.11"
  ),
  buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
  buildInfoPackage := "com.hypertino.example"
)
