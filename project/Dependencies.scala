import sbt._

object Dependencies {

  object VersionsCommon {
    val spark = "2.4.3"
    val scalalogging = "3.9.0"
    val logback = "1.2.3"
    val projectScalaVersion = "2.12.8"
    val scalatest = "3.0.1"
  }

  private val spark = Seq(
    "org.apache.spark" %% "spark-core" % VersionsCommon.spark,
    "org.apache.spark" %% "spark-sql" % VersionsCommon.spark
  )

  private val sparkSql = Seq(
    "org.apache.spark" %% "spark-sql" % VersionsCommon.spark
  )

  private val scalatest = Seq(
    "org.scalatest" %% "scalatest" % VersionsCommon.scalatest % "test"
  )  

  private val log4j = Seq("log4j" % "log4j" % "1.2.17" % "provided")

  private val scalalogging = Seq("com.typesafe.scala-logging" %% "scala-logging" % VersionsCommon.scalalogging)

  val  quizDependencies =
    spark ++
      sparkSql ++
      scalalogging ++
      scalatest ++
      log4j
}
