package com.michalplachta.snake

import akka.actor.ActorRef
import spray.json.DefaultJsonProtocol._

// TODO: Review types...
sealed trait Position {
  val x: Int
  val y: Int
}

trait FruitPosition extends Position

case class CurrentFruitPosition(x: Int, y: Int) extends FruitPosition

object CurrentFruitPosition {
  implicit val jsonFormat = jsonFormat2(CurrentFruitPosition.apply)

  def fromNew(newFruitPosition: NewFruitPosition) = {
    CurrentFruitPosition(newFruitPosition.x, newFruitPosition.y)
  }
}

case class NewFruitPosition(x: Int, y: Int) extends FruitPosition

case class PlayerPosition(x: Int, y: Int) extends Position

object PlayerPosition {
  implicit val jsonFormat = jsonFormat2(PlayerPosition.apply)
}

case class PlayerState(playerName: String, positions: List[PlayerPosition])

object PlayerState {
  implicit val jsonFormat = jsonFormat2(PlayerState.apply)
}

case class GameEvent(playerName: String, positions: List[PlayerPosition], fruitPosition: CurrentFruitPosition, score: Int)

object GameEvent {
  implicit val jsonFormat = jsonFormat4(GameEvent.apply)

  def apply(playerState: PlayerState, score: Int, fruitPosition: FruitPosition) : GameEvent =
    GameEvent(playerState.playerName, playerState.positions, CurrentFruitPosition(fruitPosition.x, fruitPosition.y), score)
}

case class PlayerJoined(actor: ActorRef)
