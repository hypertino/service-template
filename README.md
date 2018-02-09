# service-template

Service template/example with logging, docker packaging, configuration handling and dependency injection.
This template utilizes concept of _target environment_, e.g. qa, stage, prod, local, etc. Target environment should 
be provided to the service as `TARGET_ENV` environment variable or `targetEnv` java system property.
If not provided `local` environment is assumed.  

# Howto

Build docker image: 
`sbt docker`

Build & run on host with target environment:

```
sbt ';set fork := true; set connectInput in run := true; set javaOptions ++= Seq("-DtargetEnv=qa"); run'
``` 

Run in docker as background daemon:

```
docker run -d --name example-service -e TARGET_ENV=qa com.hypertino/service-template:latest
```
  
Run interactively in docker and remove after it stops: 
```
docker run -it --rm -e TARGET_ENV=qa com.hypertino/service-template:latest
```

Quick run with docker-compose 
```
TARGET_ENV=qa docker-compose up
```

# Dependencies

## [service-config](https://github.com/hypertino/service-config)

Enables configuration file where different environments specified within a single file.
See example in [application.conf](src/main/resources/application.conf)

Also adds transitive dependency to [TypeSafe Config](https://github.com/lightbend/config)

## [typesafe-config-binders](https://github.com/hypertino/typesafe-config-binders)

Read configuration to case class, example in [EntryPoint.scala](src/main/scala/com/hypertino/example/EntryPoint.scala)

```scala
  import com.hypertino.binders.config.ConfigBinders._
  private val exampleConfig = config.read[ExampleConfig]("example-service")
```

## [service-control](https://github.com/hypertino/service-control)

Provides basic `Service` interface, simple command-line with default `quit` command and shutdown hook for graceful shutdown.
See example with in [EntryPoint.scala](src/main/scala/com/hypertino/example/EntryPoint.scala)

Also adds transitive dependency to [Scaldi](http://scaldi.org/)

## [logback](https://logback.qos.ch/) with logstash backend + [scala-logging](https://github.com/typesafehub/scala-logging)

Configure in [logback.xml](src/main/resources/logback.xml), [logback-qa.xml](src/main/resources/logback-qa.xml), [logback-prod.xml](src/main/resources/logback-prod.xml) 
and add more target environment log files if needed.

## [sbt-buildinfo](https://github.com/sbt/sbt-buildinfo) plugin 

Provides compile-time access to build information. 

