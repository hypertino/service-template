enablePlugins(sbtdocker.DockerPlugin, JavaAppPackaging)

dockerfile in docker := {
  val appDir: File = stage.value
  val targetDir = "/app"

  new Dockerfile {
    from("openjdk:8")
    entryPoint("sh", "-c", s"exec $targetDir/bin/${executableScriptName.value} -DtargetEnv=$$TARGET_ENV -Dlogback.configurationFile=logback-$$TARGET_ENV.xml")
    copy(appDir, targetDir)
  }
}

// Set names for the image
imageNames in docker := Seq(
  ImageName(s"${organization.value}/${name.value}:latest"),
  ImageName(s"${organization.value}/${name.value}:v${version.value}")
)