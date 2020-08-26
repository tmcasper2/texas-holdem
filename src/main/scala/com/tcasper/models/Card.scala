package com.tcasper.models

sealed trait Suit
case object Clubs extends Suit
case object Diamonds extends Suit
case object Hearts extends Suit
case object Spades extends Suit

case class Card(value: (Int, Char, Char))
case class Hand(hand: (Card, Card)) {

  def displayHand() = s"${hand._1.value._2}${hand._1.value._3}${hand._2.value._2}${hand._2.value._3}"
}

object HandRank extends Enumeration {
  val StraightFlush, FourOfAKind, FullHouse, Flush, Straight, ThreeOfAKind, TwoPair, Pair, HighCard = Value
}

object TestCases {
  val suits = "c_h_d_s"
  val size7 = "2c3s4c5s6h 7d8h" // 7 singles
  val size6 = "2c2d3h4s5c 6s7h" // pair - 5 singles
  val size5 = "2c2d2h3s4h 5d6d" // 3 of a kind - 4 singles
  val size5_2 = "2c2d3h3s4c 5d6s" // pair - pair - 3 singles
  val size4 = "2c2h3s3d4c 4h5s" // pair - pair - pair - single
  val size4_2 = "2c2h2d3c3h 4s5d" // 3 of a kind - pair - single
  val size4_3 = "2h2d2c2s3h 4c5d" // 4 of a kind - 3 singles
  val size3 = "2h2d2c2s3h 3d4c" // 4 of a kind - pair - single
  val size3_2 = "2h2d2s3h3d 4h4d" // 3 of a kind - pair - pair
  val size3_3 = "2h2d2s3h3d 3s4d" // 3 of a kind - 3 of a kind - single
  val size2 = "2h2d2s2c3h 3d3c" // 4 of a kind - 3 of a kind

  val bottomStraight = "Ah2c3d4s5h 8c9d"
  val topStraight = "AhTcJsQdKh 4c6d"
  val middleStraight = "2h8d6cJh7c 4d5s"
  val middleStraight2 = "Kh3dJcQs9h Tc5h"
  val flush1 = "2c3c4c6c8h AsJc"

  // h, c, s, d
  val fullhouse1 = "2h2c2s3d3h 4h5c"
  val fullhouse2 = "2h3d6c3h2c 7d3s"

  val fourOfAKind1 = "7h2c7d3sKc 7s7c"
  val fourOfAKind2 = "2cKdJsKcKs 8sKh"

  val threeOfKind1 = "3h8cJs8s8h KhTc"

  val twoPair1 = "2hJc7s8c2s 6h7h"
}

