package com.hypertino.example

import com.typesafe.config.Config
import com.typesafe.scalalogging.StrictLogging

case class User(name: String, roles: Seq[String])
case class ExampleConfig(title: String, users: Map[String, User])

class ExampleService (config: Config) extends Runnable with StrictLogging {
  private val serviceName = getClass.getName

  import com.hypertino.binders.config.ConfigBinders._
  private val exampleConfig = config.read[ExampleConfig]("example-service")
  @volatile private var _stop = false

  override def run(): Unit = {
    logger.info(s"$serviceName is STARTED")
    logger.info(s"Current configuration: $exampleConfig")

    while (!_stop) {
      logger.info("working...")
      logger.trace("very hard!!!")
      Thread.sleep(2000)
    }
    logger.info(s"$serviceName is STOPPED")
  }

  def stop(): Unit = {
    logger.info(s"$serviceName is STOPPING...")
    _stop = true
  }
}
