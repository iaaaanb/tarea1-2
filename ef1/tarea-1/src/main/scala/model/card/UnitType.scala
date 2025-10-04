package model.card

/** Represents the type of unit card, determining where it can be placed */
trait UnitType

object Melee extends UnitType

object Ranged extends UnitType

object Siege extends UnitType