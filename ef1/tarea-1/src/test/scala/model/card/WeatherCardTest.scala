package model.card

class WeatherCardTest extends munit.FunSuite:

  // Normal cases
  test("create frost weather card") {
    val card = WeatherCard("Biting Frost", Frost)
    assertEquals(card.name, "Biting Frost")
    assertEquals(card.weatherType, Frost)
  }

  test("create fog weather card") {
    val card = WeatherCard("Impenetrable Fog", Fog)
    assertEquals(card.weatherType, Fog)
  }

  test("create rain weather card") {
    val card = WeatherCard("Torrential Rain", Rain)
    assertEquals(card.weatherType, Rain)
  }

  test("create clear weather card") {
    val card = WeatherCard("Clear Skies", Clear)
    assertEquals(card.weatherType, Clear)
  }

  // Edge cases
  test("create weather card with empty name") {
    val card = WeatherCard("", Frost)
    assertEquals(card.name, "")
  }

  test("create weather card with very long name") {
    val longName = "a" * 1000
    val card = WeatherCard(longName, Frost)
    assertEquals(card.name.length, 1000)
  }

  // toString tests
  test("weather card toString includes all information") {
    val card = WeatherCard("Biting Frost", Frost)
    val str = card.toString
    assert(str.contains("Biting Frost"))
    assert(str.contains("Frost"))
  }

  // Equality tests
  test("two weather cards with same attributes are equal") {
    val card1 = WeatherCard("Biting Frost", Frost)
    val card2 = WeatherCard("Biting Frost", Frost)
    assertEquals(card1, card2)
  }

  test("weather cards with different names are not equal") {
    val card1 = WeatherCard("Frost", Frost)
    val card2 = WeatherCard("Ice", Frost)
    assertNotEquals(card1, card2)
  }

  test("weather cards with different types are not equal") {
    val card1 = WeatherCard("Storm", Frost)
    val card2 = WeatherCard("Storm", Rain)
    assertNotEquals(card1, card2)
  }

  test("weather card is not equal to unit card") {
    val weatherCard = WeatherCard("Frost", Frost)
    val unitCard = UnitCard("Soldier", 5, Melee)
    assertNotEquals(weatherCard: Any, unitCard)
  }

  test("weather card is not equal to non-card object") {
    val card = WeatherCard("Frost", Frost)
    assertNotEquals(card: Any, "not a card")
    assertNotEquals(card: Any, null)
  }

  // HashCode tests
  test("equal weather cards have same hashCode") {
    val card1 = WeatherCard("Frost", Frost)
    val card2 = WeatherCard("Frost", Frost)
    assertEquals(card1.hashCode(), card2.hashCode())
  }

  test("weather cards can be used in sets") {
    val card1 = WeatherCard("Frost", Frost)
    val card2 = WeatherCard("Frost", Frost)
    val card3 = WeatherCard("Rain", Rain)

    val set = Set(card1, card2, card3)
    assertEquals(set.size, 2) // card1 and card2 are duplicates
  }

  // Card trait compatibility
  test("weather card can be assigned to Card trait") {
    val card: Card = WeatherCard("Frost", Frost)
    assertEquals(card.name, "Frost")
  }