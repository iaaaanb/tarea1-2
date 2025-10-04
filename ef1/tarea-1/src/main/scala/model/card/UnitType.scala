package model.card

/** Represents the classification of a unit card, determining which
 * battlefield zone it can be placed in.
 *
 * There are three types:
 * - [[Melee]]: Close combat zone
 * - [[Ranged]]: Ranged combat zone
 * - [[Siege]]: Siege zone
 */
trait UnitType

/** Close combat unit type */
object Melee extends UnitType

/** Ranged combat unit type */
object Ranged extends UnitType

/** Siege unit type */
object Siege extends UnitType