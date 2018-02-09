package com.hypertino.example

import java.util.Locale

import com.hypertino.service.config.ConfigModule
import com.hypertino.service.control.ConsoleModule
import com.hypertino.service.control.api.ServiceController
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

    implicit val injector = new ExampleServiceModule ::
      new ConsoleModule(Some("exampleService")) ::
      ConfigModule(environment=Some(env))

    inject[ServiceController].run()
  }
}
