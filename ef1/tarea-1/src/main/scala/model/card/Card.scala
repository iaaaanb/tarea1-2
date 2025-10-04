package model.card

/** Base trait for all cards in the game.
 * Every card has a name that identifies it.
 */
trait Card:
  /** The name of the card */
  val name: String