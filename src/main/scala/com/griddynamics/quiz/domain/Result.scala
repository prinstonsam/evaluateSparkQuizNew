package com.griddynamics.quiz.domain

final case class Result(airline: String, airport: String, avgDelay: Long)

object Result {
  implicit val eq: Eq[Result] = (x: Result, y: Result) =>
    x.avgDelay == y.avgDelay && x.airline.equalsIgnoreCase(y.airline) && x.airport.equalsIgnoreCase(y.airport)
}

/*
object Ratio{

  implicit val eq: Eq[Ratio] = (x: Ratio, y: Ratio) => x.num * y.den == x.den * y.num
}
 */
