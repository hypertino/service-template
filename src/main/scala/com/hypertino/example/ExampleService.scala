package com.hypertino.example

import com.hypertino.service.control.api.Service
import com.typesafe.config.Config
import com.typesafe.scalalogging.StrictLogging
import monix.execution.{Cancelable, Scheduler}
import scaldi.{Injectable, Injector}

import scala.concurrent.Future
import scala.concurrent.duration._

case class User(name: String, roles: Seq[String])
case class ExampleConfig(title: String, users: Map[String, User])

class ExampleService (implicit val injector: Injector) extends Service with Injectable with StrictLogging {
  private val serviceName = getClass.getName
  private val config = inject[Config]
  import com.hypertino.binders.config.ConfigBinders._
  private val exampleConfig = config.read[ExampleConfig]("example-service")
  private var cancelable: Option[Cancelable] = None

  private implicit val scheduler = Scheduler.forkJoin(
    name="example-service-scheduler",
    parallelism=4,
    maxThreads=128,
    daemonic=false
  )

  logger.info(s"$serviceName is INITIALIZED")

  override def startService(): Unit = {
    logger.info(s"$serviceName is STARTED")
    logger.info(s"Current configuration: $exampleConfig")

    cancelable = Some(scheduler.scheduleAtFixedRate(0.seconds,5.seconds) {
      logger.info("working...")
      logger.trace("very hard!!!")
    })
    Thread.sleep(2000)
  }

  override def stopService(controlBreak: Boolean, timeout: FiniteDuration): Future[Unit] = Future {
    logger.info(s"$serviceName is STOPPING...")
    cancelable.foreach(_.cancel)
    scheduler.shutdown()
    logger.info(s"$serviceName is STOPPED")
  }
}
