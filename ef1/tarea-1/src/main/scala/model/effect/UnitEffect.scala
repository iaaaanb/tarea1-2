package model.effect

/** Represents a special ability that a unit card can have.
 *
 * Unit effects are activated when the card is placed on the battlefield
 * and remain active while the card is on the field.
 *
 * Available effects:
 * - [[MoraleBoost]]: Adds +1 strength to all other cards in the same row
 * - [[CloseBond]]: Doubles strength of all cards with the same name in the row
 */
trait UnitEffect

/** Morale Boost effect: When placed, adds +1 strength to all other cards in its row */
object MoraleBoost extends UnitEffect

/** Close Bond effect: Doubles strength of all cards with same name in the row, including itself */
object CloseBond extends UnitEffect