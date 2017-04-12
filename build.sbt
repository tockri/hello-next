import sbt.Keys.resolvers
import sbt.Resolver


lazy val commonSettings = Seq(
  scalaVersion := "2.11.8",
  version := "1.0.0-SNAPSHOT",
  //resolvers += ("Local Maven repo" at "file:///" + Path.userHome.absolutePath + "/.m2/repository"),
  resolvers += Resolver.mavenLocal,
  libraryDependencies ++= Seq(
    "joda-time" % "joda-time" % "2.9.9",
    "ch.qos.logback" % "logback-classic" % "1.1.7",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0"
  )
)

lazy val domain = (project in file("domain"))
  .settings(commonSettings:_*)
  .settings(
    name := "hello-domain"
  )

lazy val infra = (project in file("infra"))
  .settings(commonSettings:_*)
  .settings(
    name := "hello-infra",
    libraryDependencies ++= Seq(
      "mysql" % "mysql-connector-java" % "5.1.36",
      "org.scalikejdbc" %% "scalikejdbc" % "2.5.1",
      "org.scalikejdbc" %% "scalikejdbc-config" % "2.5.1"
    )
  )
  .dependsOn(domain)

lazy val root = (project in file("."))
  .settings(commonSettings:_*)
  .settings(
    name := "hello",
    libraryDependencies ++= Seq(
      cache,
      ws,
      evolutions,
      jdbc,
      "org.scalikejdbc" %% "scalikejdbc-play-dbapi-adapter" % "2.5.1",
      "net.codingwell" %% "scala-guice" % "4.1.0",
      "org.scalikejdbc" %% "scalikejdbc-test" % "2.5.1" % Test,
      "org.scalatest" %% "scalatest" % "3.0.1" % Test
    ),
    javaOptions in Test += "-Dconfig.resource=test.conf",
    parallelExecution in Test := false
  )
  .dependsOn(infra, domain)
  .enablePlugins(PlayScala)

