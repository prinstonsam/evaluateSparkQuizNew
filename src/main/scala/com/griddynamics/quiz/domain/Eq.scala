package com.griddynamics.quiz.domain

import syntax.eq._

trait Eq[T] {
  def ===(x: T, y: T): Boolean
}

object Eq {
  implicit def compareList[T](implicit eq: Eq[T]): Eq[List[T]] = (x: List[T], y: List[T]) => {
    x.zip(y).forall { case (x: T, y: T) => x === y }
  }
}