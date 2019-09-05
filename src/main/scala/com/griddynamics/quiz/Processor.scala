package com.griddynamics.quiz

import com.griddynamics.quiz.domain.{Airline, Airport, Flights, Result}
import org.apache.spark.sql._

object Processor {

  implicit def airlineEncoder: Encoder[Airline] = Encoders.product

  implicit def airportEncoder: Encoder[Airport] = Encoders.product

  implicit def flightsEncoder: Encoder[Flights] = Encoders.product

  implicit def resultEncoder: Encoder[Result] = Encoders.product

  def process(spark: SparkSession): Dataset[Result] = {

    val airlines = spark.read.format("csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load("/app/airlines.csv")
      .map(row => Airline(row.getAs[String]("IATA_CODE"), row.getAs[String]("AIRLINE")))

    val airports = spark.read.format("csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load("/app/airports.csv")
      .map(row => Airport(row.getAs[String]("IATA_CODE"), row.getAs[String]("AIRPORT")))

    import org.apache.spark.sql.functions._

    spark.read.format("csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load("/app/flights.csv")
      .map(row =>
        Flights(
          row.getAs[String]("AIRLINE"),
          row.getAs[String]("DESTINATION_AIRPORT"),
          row.getAs[Int]("AIRLINE_DELAY")
        )
      )
      .filter(flight => flight.airlineDelay > 0)
      .join(airlines, "airline")
      .join(airports, "airport")
      .groupBy("airline", "airport")
      .agg(avg("airlineDelay"))
      .map {
        case Row(airline: String, airport: String, avgDelay: Double) => Result(airline, airport, Math.round(avgDelay))
      }
      .sort(col("avgDelay").desc, col("airline").asc, col("airport").asc)
  }
}
