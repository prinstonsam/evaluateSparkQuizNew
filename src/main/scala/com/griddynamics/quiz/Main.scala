package com.griddynamics.quiz

import com.griddynamics.quiz.domain.Result
import com.typesafe.scalalogging.LazyLogging
import org.apache.spark.scheduler.{SparkListener, SparkListenerStageCompleted}
import org.apache.spark.sql.SparkSession

import scala.collection.mutable.ListBuffer

object Main extends LazyLogging {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("local")
      .appName("bigdata-competition")
      .getOrCreate()

    val myListener = new CustomListener
    spark.sparkContext.addSparkListener(myListener)

    val results = Processor.process(spark)

    val resultsTop10 = results.take(10)

    println(s"cpu time: %d".format(myListener.stageMetricsData.sum))

    //check results
    import com.griddynamics.quiz.domain.syntax.eq._

    if (resultsTop10.toList === makePerfectResult()) println("Result is correct") else println("Result isn't correct")

    spark.stop()
  }

  def makePerfectResult(): List[Result] = {
    List(
      Result("OO", "BRD", 132),
      Result("DL", "STX", 119),
      Result("OO", "BJI", 101),
      Result("EV", "MCO", 95),
      Result("OO", "CWA", 88),
      Result("OO", "TVC", 88),
      Result("DL", "OGG", 86),
      Result("EV", "EYW", 82),
      Result("OO", "ESC", 82),
      Result("HA", "PPG", 81)
    )
  }

  class CustomListener extends SparkListener {

    val stageMetricsData: ListBuffer[Long] = ListBuffer.empty[Long]

    override def onStageCompleted(stageCompleted: SparkListenerStageCompleted): Unit = {
      stageMetricsData += stageCompleted.stageInfo.taskMetrics.executorCpuTime / 1000000
    }
  }

}

