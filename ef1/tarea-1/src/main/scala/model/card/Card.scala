package model.card

/** Base trait for all cards in the game.
 *
 * All cards must have a name that identifies them uniquely.
 * Cards can be either unit cards (placed on battlefield zones) 
 * or weather cards (placed in the shared weather zone).
 */
trait Card:
  /** The name of the card */
  val name: String