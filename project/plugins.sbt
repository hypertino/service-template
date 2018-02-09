resolvers ++= Seq(
  Resolver.sonatypeRepo("public")
)

addSbtPlugin("se.marcuslonnberg" % "sbt-docker" % "1.4.1")
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.3.3")
addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.7.0")