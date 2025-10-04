package model.card

import model.effect.*

class UnitCardTest extends munit.FunSuite:

  // Normal cases
  test("create melee unit card with valid parameters") {
    val card = UnitCard("Infantry", 5, Melee)
    assertEquals(card.name, "Infantry")
    assertEquals(card.strength, 5)
    assertEquals(card.unitType, Melee)
  }

  test("create ranged unit card") {
    val card = UnitCard("Archer", 3, Ranged)
    assertEquals(card.unitType, Ranged)
  }

  test("create siege unit card") {
    val card = UnitCard("Catapult", 8, Siege)
    assertEquals(card.unitType, Siege)
  }

  // Edge cases
  test("create unit card with zero strength") {
    val card = UnitCard("Weak Unit", 0, Melee)
    assertEquals(card.strength, 0)
  }

  test("create unit card with negative strength") {
    val card = UnitCard("Cursed Unit", -5, Melee)
    assertEquals(card.strength, -5)
  }

  test("create unit card with empty name") {
    val card = UnitCard("", 5, Melee)
    assertEquals(card.name, "")
  }

  test("create unit card with very high strength") {
    val card = UnitCard("Hero", 100, Melee)
    assertEquals(card.strength, 100)
  }

  test("create unit card with Unicode name") {
    val card = UnitCard("騎士", 7, Melee) // Japanese "knight"
    assertEquals(card.name, "騎士")
  }

  test("create unit card with very long name") {
    val longName = "a" * 1000
    val card = UnitCard(longName, 5, Melee)
    assertEquals(card.name.length, 1000)
  }

  test("create unit card with special characters in name") {
    val card = UnitCard("Sir O'Malley-Jones III", 5, Melee)
    assertEquals(card.name, "Sir O'Malley-Jones III")
  }

  // Effect tests
  test("create unit card with no effect") {
    val card = UnitCard("Infantry", 5, Melee)
    assert(card.effect.isEmpty)
  }

  test("create unit card with MoraleBoost effect") {
    val card = UnitCard("Commander", 6, Melee, Some(MoraleBoost))
    assert(card.effect.isDefined)
    assertEquals(card.effect.get, MoraleBoost)
  }

  test("create unit card with CloseBond effect") {
    val card = UnitCard("Twin Archer", 4, Ranged, Some(CloseBond))
    assert(card.effect.isDefined)
    assertEquals(card.effect.get, CloseBond)
  }

  // toString tests
  test("unit card toString includes all information") {
    val card = UnitCard("Knight", 7, Melee)
    val str = card.toString
    assert(str.contains("Knight"))
    assert(str.contains("7"))
    assert(str.contains("Melee"))
  }

  test("unit card toString includes effect when present") {
    val card = UnitCard("Commander", 6, Melee, Some(MoraleBoost))
    val str = card.toString
    assert(str.contains("MoraleBoost"))
  }

  test("unit card toString shows no effect when absent") {
    val card = UnitCard("Infantry", 5, Melee)
    val str = card.toString
    assert(!str.contains("effect="))
  }

  // Equality tests
  test("two unit cards with same attributes are equal") {
    val card1 = UnitCard("Soldier", 5, Melee)
    val card2 = UnitCard("Soldier", 5, Melee)
    assertEquals(card1, card2)
  }

  test("unit cards with different names are not equal") {
    val card1 = UnitCard("Soldier", 5, Melee)
    val card2 = UnitCard("Knight", 5, Melee)
    assertNotEquals(card1, card2)
  }

  test("unit cards with different strength are not equal") {
    val card1 = UnitCard("Soldier", 5, Melee)
    val card2 = UnitCard("Soldier", 7, Melee)
    assertNotEquals(card1, card2)
  }

  test("unit cards with different types are not equal") {
    val card1 = UnitCard("Soldier", 5, Melee)
    val card2 = UnitCard("Soldier", 5, Ranged)
    assertNotEquals(card1, card2)
  }

  test("unit cards with different effects are not equal") {
    val card1 = UnitCard("Hero", 7, Melee, Some(MoraleBoost))
    val card2 = UnitCard("Hero", 7, Melee, Some(CloseBond))
    assertNotEquals(card1, card2)
  }

  test("unit card with effect not equal to card without effect") {
    val card1 = UnitCard("Hero", 7, Melee, Some(MoraleBoost))
    val card2 = UnitCard("Hero", 7, Melee, None)
    assertNotEquals(card1, card2)
  }

  test("two unit cards with same effect are equal") {
    val card1 = UnitCard("Hero", 7, Melee, Some(MoraleBoost))
    val card2 = UnitCard("Hero", 7, Melee, Some(MoraleBoost))
    assertEquals(card1, card2)
  }

  test("equal effects with different strength are not equal cards") {
    val card1 = UnitCard("Hero", 5, Melee, Some(MoraleBoost))
    val card2 = UnitCard("Hero", 7, Melee, Some(MoraleBoost))
    assertNotEquals(card1, card2)
  }

  test("unit card is not equal to weather card") {
    val unitCard = UnitCard("Soldier", 5, Melee)
    val weatherCard = WeatherCard("Frost", Frost)
    assertNotEquals(unitCard: Any, weatherCard)
  }

  test("unit card is not equal to non-card object") {
    val card = UnitCard("Soldier", 5, Melee)
    assertNotEquals(card: Any, "not a card")
    assertNotEquals(card: Any, 42)
  }

  // HashCode tests
  test("equal unit cards have same hashCode") {
    val card1 = UnitCard("Soldier", 5, Melee)
    val card2 = UnitCard("Soldier", 5, Melee)
    assertEquals(card1.hashCode(), card2.hashCode())
  }

  test("unit cards can be used in sets") {
    val card1 = UnitCard("Soldier", 5, Melee)
    val card2 = UnitCard("Soldier", 5, Melee)
    val card3 = UnitCard("Knight", 7, Melee)

    val set = Set(card1, card2, card3)
    assertEquals(set.size, 2) // card1 and card2 are duplicates
  }

  test("unit cards with effects can be used in sets") {
    val card1 = UnitCard("Hero", 7, Melee, Some(MoraleBoost))
    val card2 = UnitCard("Hero", 7, Melee, Some(MoraleBoost))
    val card3 = UnitCard("Hero", 7, Melee, Some(CloseBond))

    val set = Set(card1, card2, card3)
    assertEquals(set.size, 2) // card1 and card2 are duplicates
  }