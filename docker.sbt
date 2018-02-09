enablePlugins(DockerPlugin)

dockerfile in docker := {
  val jarFile: File = sbt.Keys.`package`.in(Compile, packageBin).value
  val classpath = (managedClasspath in Compile).value
  val mainclass = mainClass.in(Compile, packageBin).value.getOrElse(sys.error("Expected exactly one main class"))
  val jarTarget = s"/app/${jarFile.getName}"
  val classpathString = classpath.files.map("/app/" + _.getName)
    .mkString(":") + ":" + jarTarget
  new Dockerfile {
    from("openjdk:8")
    add(classpath.files, "/app/")
    add(jarFile, jarTarget)
    entryPoint("sh", "-c", s"exec java -DtargetEnv=$$TARGET_ENV -Dlogback.configurationFile=logback-$$TARGET_ENV.xml -cp $classpathString $mainclass")
  }
}

// Set names for the image
imageNames in docker := Seq(
  ImageName(s"${organization.value}/${name.value}:latest"),
  ImageName(s"${organization.value}/${name.value}:v${version.value}")
)