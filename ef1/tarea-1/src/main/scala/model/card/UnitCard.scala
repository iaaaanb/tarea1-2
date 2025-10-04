package model.card

import model.effect.UnitEffect

/** Represents a unit card that can be placed on the battlefield.
 *
 * Unit cards have strength values that contribute to the player's
 * total battlefield strength. Each unit has a type that determines
 * which zone it can be placed in, and may have a special ability.
 *
 * @param name the name of the card
 * @param strength the base strength value of the unit
 * @param unitType the type of unit (Melee, Ranged, or Siege)
 * @param effect optional special ability (None if no ability)
 */
class UnitCard(
                val name: String,
                val strength: Int,
                val unitType: UnitType,
                val effect: Option[UnitEffect] = None
              ) extends Card:

  override def toString: String =
    val effectStr = effect.map(e => s", effect=$e").getOrElse("")
    s"UnitCard($name, strength=$strength, type=$unitType$effectStr)"

  override def equals(obj: Any): Boolean =
    if obj.isInstanceOf[UnitCard] then
      val other = obj.asInstanceOf[UnitCard]
      name == other.name &&
        strength == other.strength &&
        unitType == other.unitType &&
        effect == other.effect
    else false

  override def hashCode(): Int =
    (classOf[UnitCard], name, strength, unitType, effect).hashCode()