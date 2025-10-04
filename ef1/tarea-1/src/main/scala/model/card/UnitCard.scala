package model.card

/** Represents a unit card that can be placed on the battlefield.
 *
 * @param name the name of the card
 * @param strength the base strength value of the unit
 * @param unitType the type of unit (Melee, Ranged, or Siege)
 */
class UnitCard(
                val name: String,
                val strength: Int,
                val unitType: UnitType
              ) extends Card:

  override def toString: String =
    s"UnitCard($name, strength=$strength, type=$unitType)"

  
  override def equals(obj: Any): Boolean =
    if obj.isInstanceOf[UnitCard] then
      val other = obj.asInstanceOf[UnitCard]
      name == other.name &&
        strength == other.strength &&
        unitType == other.unitType
    else false

  override def hashCode(): Int =
    (classOf[UnitCard], name, strength, unitType).hashCode()