package model.card

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

  // toString tests
  test("unit card toString includes all information") {
    val card = UnitCard("Knight", 7, Melee)
    val str = card.toString
    assert(str.contains("Knight"))
    assert(str.contains("7"))
    assert(str.contains("Melee"))
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