package com.tcasper

import com.tcasper.models.{Card, Hand}

object Main extends App {

  println("[Main with Card] --> Enter the given hand: ")
  val cardString = scala.io.StdIn.readLine()

  println("Unprocessed Card String ==> " + cardString)

  val cardArray = cardString.split(" ")
  val boardCards = cardArray(0)

  println()
  println("boardcards = " + boardCards)

  println()
  val handsStringArray = cardArray.tail
  print("hands = ")
  handsStringArray.map(x => print(x + " "))
  println()

  val boardCardsArray = processBoardCards(boardCards)
  print("boardcards array = ")
  boardCardsArray.map(x => print(" | " + x + " | "))
  println()
  val handsArray = processHandCards(handsStringArray)
  print("hands array = ")
  handsArray.map(x => print(" | " + x + " | "))

  val handsMap =
    for (hand <- handsArray) yield process(boardCardsArray, hand)

  print("Final HandsMap = ")
  handsMap.sortBy(_._2).map(p => println(p._1.displayHand() + " Value = " + p._2))

  def process(boardCards: Array[Card], hand: Hand): (Hand, Int) = {

    // Combine the hand with the board cards first
    val combined = combineBoardCardsWithHand(boardCards, hand)

    val sortedCards = sortCombinedCards(combined.toList)
    println("sorted cards = " + sortedCards)

    //Cards will be grouped by value in descending order size groups
    val groupedCards = splitCardsByGroup(sortedCards)
    println("grouped cards = " + groupedCards)

    val handValue = determineHandValue(groupedCards, hand)

    handValue
  }

  def determineHandValue(groupedCards: List[List[Card]], originalHand: Hand): (Hand, Int) = {
    val ungrouped = groupedCards.flatten.sortBy(_.value._1)
    groupedCards.length match {
      case 7 =>
        println("Size 7")
        // 7 distinct card values
        if (isStraight(ungrouped) && isFlush(ungrouped)) {
          (originalHand, StraightFlush)
        } else if (isFlush(ungrouped)) {
          (originalHand, Flush)
        } else if (isStraight(ungrouped)) {
          (originalHand, Straight)
        } else (originalHand, HighCard)

      case 6 =>
        println("Size 6")
        // Pair, 5 singles
        if (isStraight(ungrouped) && isFlush(ungrouped)) {
          (originalHand, StraightFlush)
        } else if (isFlush(ungrouped)) {
          (originalHand, Flush)
        } else if (isStraight(ungrouped)) {
          (originalHand, Straight)
        } else (originalHand, Pair)

      case 5 =>
        println("Size 5")
        // 3 of a kind, 4 singles
        if (isFlush(ungrouped)) {
          (originalHand, Flush)
        }
        if (groupedCards.head.length == 3) {
          (originalHand, ThreeOfAKind)

          // pair, pair, 3 singes
        } else {
          (originalHand, TwoPair)
        }

      case 4 =>
        println("Size 4")
        if (isFlush(ungrouped)) {
          (originalHand, Flush)
        }
        // 4 of a kind, 3 singles
        if (groupedCards.head.length == 4) {
          (originalHand, FourOfAKind)
        }

        // 3 of a kind, pair, 2 singles
        else if (groupedCards.head.length == 3) {
          (originalHand, FullHouse)
        }

        // pair, pair, pair, single
        else {
          (originalHand, TwoPair) //TODO Make sure to sort and grab the highest 2 pairs
        }

      case 3 =>
        println("Size 3")
        if (isFlush(ungrouped)) {
          (originalHand, Flush)
        }
        // 4 of a kind, pair, single
        if (groupedCards.head.length == 4) {
          (originalHand, FourOfAKind)
        }

        // 3 of a kind, pair, pair OR 3 of a kind, 3 of a kind, single
        //TODO make sure to sort for the highest value full house
        else {
          (originalHand, FullHouse)
        }

      case 2 =>
        println("Size 2")
        if (isFlush(ungrouped)) {
          (originalHand, Flush)
        }
        // 4 of a kind, 3 of a kind
        else (originalHand, FourOfAKind)

      case _ =>
        println("no match found")
        (Hand(Card(-99, 'X', 'X'), Card(-99, 'X', 'X')), -99)
    }
  }

  def combineBoardCardsWithHand(boardCards: Array[Card], hand: Hand): Array[Card] = {
    boardCards :+ hand.hand._1 :+ hand.hand._2
  }

  def sortCombinedCards(cards: List[Card]): List[Card] = {
    println("List from combined cards = " + cards)
    val sortedList = cards.sortBy(_.value._1)
    sortedList
  }

  def splitCardsByGroup(cards: List[Card]): List[List[Card]] = {
    val groupedBy = cards.groupBy(_.value._1)
    val sortedGroupedValues = groupedBy.values.toList.sortBy(_.length)(Ordering[Int].reverse)
    sortedGroupedValues
  }

  def isFlush(cards: List[Card]): Boolean = {
    println("#### Checking for flush...")
    val grouped = cards.groupBy(_.value._3)
    println(s"#### Grouped = $grouped")
    val groupedListsOfSuits = grouped.values
    println(s"#### Grouped list of suits = ${groupedListsOfSuits}")
    for (g <- groupedListsOfSuits) {
      if (g.length >= 5) {
        println(s"#### Found a group of 5 suits, returning true")
        return true
      }
    }
    false
  }

  def isStraight(cards: List[Card]): Boolean = {
    println("#### Checking for a straight...")
    if (containsAnAce(cards)) {
      isStraightWithAce(cards)
    } else {
      println("#### No ACES found, checking deck for straight...")
      println(s"cards(0) = ${cards(0).value._1}")
      println(s"cards(1) = ${cards(1).value._1}")
      println(s"cards(2) = ${cards(2).value._1}")
      println(s"cards(3) = ${cards(3).value._1}")
      println(s"cards(4) = ${cards(4).value._1}")
      println(s"cards(5) = ${cards(5).value._1}")
      println(s"cards(6) = ${cards(6).value._1}")
      for (i <- 0 until 4) {
        println(s"iteration $i")
        if ((cards(i + 1).value._1 != cards(i).value._1 + 1) && (i > 2)) {
          println("Returning false")
          println(s"cards(i + 1).value._1 != cards(i).value._1 + 1 = ${cards(i + 1).value._1 != cards(i).value._1 + 1}")
          return false
        }
      }
      println("#### Straight found, returning true")
      true
    }
  }

  def isStraightWithAce(cards: List[Card]): Boolean = {
    println("#### Contains an ACE...")
    println(s"cards(0) = ${cards(0).value._1}")
    println(s"cards(1) = ${cards(1).value._1}")
    println(s"cards(2) = ${cards(2).value._1}")
    println(s"cards(3) = ${cards(3).value._1}")
    println(s"cards(4) = ${cards(4).value._1}")
    val bottomStraight = cards(1).value._1 == 2 && cards(2).value._1 == 3 && cards(3).value._1 == 4 && cards(4).value._1 == 5
    println("#### Bottom straight = " + bottomStraight)
    val topStraight = cards(3).value._1 == 10 && cards(4).value._1 == 11 && cards(5).value._1 == 12 && cards(6).value._1 == 13
    println("#### Top straight = " + topStraight)
    if (bottomStraight || topStraight) {
      println("Bottom or top straight found, returning true")
      true
    } else {
      println("#### No bottom or top straight found, checking middle...")
      for (i <- 0 until 4) {
        if ((cards(i + 1).value._1 != cards(i).value._1 + 1) && (i > 2)) {
          return false
        }
      }
      true
    }
  }

  def containsAnAce(cards: List[Card]): Boolean = {
    for (card <- cards) {
      if (card.value._1 == 1) {
        return true
      }
    }
    false
  }

  def processBoardCards(str: String) = {
    val boardCardArray = new Array[Card](5)
    boardCardArray(0) = Card(cardValueMap(str(0)), str(0).toUpper, str(1))
    boardCardArray(1) = Card(cardValueMap(str(2)), str(2).toUpper, str(3))
    boardCardArray(2) = Card(cardValueMap(str(4)), str(4).toUpper, str(5))
    boardCardArray(3) = Card(cardValueMap(str(6)), str(6).toUpper, str(7))
    boardCardArray(4) = Card(cardValueMap(str(8)), str(8).toUpper, str(9))
    boardCardArray
  }

  def processHandCards(handsStringArray: Array[String]): Array[Hand] = {
    val handsArray =
      for (
        str <- handsStringArray
      ) yield Hand(Card(cardValueMap(str.charAt(0)), str.charAt(0).toUpper, str.charAt(1)), Card(cardValueMap(str.charAt(2)), str.charAt(2).toUpper, str.charAt(3)))
    handsArray
  }

  final val cardValueMap = Map(
    'A' -> 1,
    '2' -> 2,
    '3' -> 3,
    '4' -> 4,
    '5' -> 5,
    '6' -> 6,
    '7' -> 7,
    '8' -> 8,
    '9' -> 9,
    'T' -> 10,
    'J' -> 11,
    'Q' -> 12,
    'K' -> 13)

  final val StraightFlush = 9
  final val FourOfAKind = 8
  final val FullHouse = 7
  final val Flush = 6
  final val Straight = 5
  final val ThreeOfAKind = 4
  final val TwoPair = 3
  final val Pair = 2
  final val HighCard = 1
}
