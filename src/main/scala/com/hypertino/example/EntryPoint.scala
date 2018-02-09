package com.hypertino.example

import java.util.Locale

import com.hypertino.service.config.ConfigLoader
import com.typesafe.scalalogging.StrictLogging
import scaldi.Injectable

object EntryPoint extends Injectable with StrictLogging {
  def main(args: Array[String]): Unit = {
    Locale.setDefault(Locale.US)
    if (System.getProperty("targetEnv") == null || System.getProperty("targetEnv").isEmpty) {
      System.setProperty("targetEnv", "local")
    }
    val env = System.getProperty("targetEnv")

    logger.info(s"Example Service is starting @ '$env' ${BuildInfo.toString}")

    val config = ConfigLoader(environment = Some(env))
    val exampleService = new ExampleService(config)
    Runtime.getRuntime.addShutdownHook(new Thread() {
      override def run() {
        exampleService.stop()
      }
    })
    exampleService.run()
  }
}
