resolvers ++= Seq(
  Resolver.sonatypeRepo("public")
)

addSbtPlugin("se.marcuslonnberg" % "sbt-docker" % "1.4.1")
addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.7.0")