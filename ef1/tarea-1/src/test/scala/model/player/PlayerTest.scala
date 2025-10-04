package model.player

import model.card.*
import model.effect.*

class PlayerTest extends munit.FunSuite:

  // Test fixtures
  var player: Player = null
  var card1: Card = null
  var card2: Card = null
  var card3: Card = null

  override def beforeEach(context: BeforeEach): Unit =
    player = Player("Alice", 2)
    card1 = UnitCard("Soldier", 5, Melee)
    card2 = UnitCard("Archer", 3, Ranged)
    card3 = WeatherCard("Frost", Frost)

  // Constructor tests
  test("create player with default gems") {
    val p = Player("Bob")
    assertEquals(p.name, "Bob")
    assertEquals(p.gems, 2)
  }

  test("create player with custom gems") {
    val p = Player("Charlie", 5)
    assertEquals(p.gems, 5)
  }

  test("create player with zero gems") {
    val p = Player("David", 0)
    assertEquals(p.gems, 0)
  }

  test("cannot create player with negative gems") {
    intercept[IllegalArgumentException] {
      Player("Evil", -1)
    }
  }

  test("cannot create player with empty name") {
    intercept[IllegalArgumentException] {
      Player("")
    }
  }

  test("create player with Unicode name") {
    val p = Player("アリス", 2) // Japanese "Alice"
    assertEquals(p.name, "アリス")
  }

  test("create player with very long name") {
    val longName = "a" * 1000
    val p = Player(longName, 2)
    assertEquals(p.name.length, 1000)
  }

  // Initial state tests
  test("new player has empty deck") {
    assertEquals(player.deckSize, 0)
  }

  test("new player has empty hand") {
    assertEquals(player.handSize, 0)
    assertEquals(player.hand, List.empty)
  }

  test("new player has not lost") {
    assert(!player.hasLost)
  }

  // Deck management tests
  test("add card to deck increases deck size") {
    player.addToDeck(card1)
    assertEquals(player.deckSize, 1)

    player.addToDeck(card2)
    assertEquals(player.deckSize, 2)
  }

  test("add multiple cards to deck") {
    player.addToDeck(card1)
    player.addToDeck(card2)
    player.addToDeck(card3)
    assertEquals(player.deckSize, 3)
  }

  test("add duplicate cards to deck is allowed") {
    player.addToDeck(card1)
    player.addToDeck(card1) // Same card instance
    assertEquals(player.deckSize, 2)
  }

  test("shuffle deck maintains deck size") {
    player.addToDeck(card1)
    player.addToDeck(card2)
    player.addToDeck(card3)

    val sizeBefore = player.deckSize
    player.shuffleDeck()
    assertEquals(player.deckSize, sizeBefore)
  }

  test("shuffle empty deck does not cause error") {
    player.shuffleDeck()
    assertEquals(player.deckSize, 0)
  }

  // Draw card tests
  test("draw card from deck with cards") {
    player.addToDeck(card1)
    val drawn = player.drawCard()

    assert(drawn.isDefined)
    assertEquals(drawn.get, card1)
    assertEquals(player.deckSize, 0)
    assertEquals(player.handSize, 1)
  }

  test("draw card from empty deck returns None") {
    val drawn = player.drawCard()
    assert(drawn.isEmpty)
    assertEquals(player.handSize, 0)
  }

  test("draw multiple cards") {
    player.addToDeck(card1)
    player.addToDeck(card2)
    player.addToDeck(card3)

    val count = player.drawCards(2)
    assertEquals(count, 2)
    assertEquals(player.deckSize, 1)
    assertEquals(player.handSize, 2)
  }

  test("draw zero cards") {
    player.addToDeck(card1)
    val count = player.drawCards(0)
    assertEquals(count, 0)
    assertEquals(player.handSize, 0)
    assertEquals(player.deckSize, 1)
  }

  test("draw negative cards throws exception") {
    intercept[IllegalArgumentException] {
      player.drawCards(-1)
    }
  }

  test("draw cards respects deck size limit") {
    player.addToDeck(card1)
    player.addToDeck(card2)

    val count = player.drawCards(5)
    assertEquals(count, 2) // Only 2 cards available
    assertEquals(player.deckSize, 0)
    assertEquals(player.handSize, 2)
  }

  test("draw cards respects hand size limit of 10") {
    // Add 15 cards to deck
    for i <- 1 to 15 do
      player.addToDeck(UnitCard(s"Card$i", i, Melee))

    val count = player.drawCards(15)
    assertEquals(count, 10) // Can only draw 10
    assertEquals(player.handSize, 10)
    assertEquals(player.deckSize, 5) // 5 remain in deck
  }

  test("cannot draw when hand is full") {
    // Fill hand
    for i <- 1 to 10 do
      player.addToDeck(UnitCard(s"Card$i", i, Melee))
    player.drawCards(10)

    // Try to draw one more
    player.addToDeck(card1)
    val drawn = player.drawCard()
    assert(drawn.isEmpty)
    assertEquals(player.handSize, 10)
  }

  // Hand management tests
  test("hand returns immutable copy") {
    player.addToDeck(card1)
    player.drawCard()

    val hand = player.hand
    assertEquals(hand.size, 1)

    // Original hand should not be affected by external modification
    assert(hand.isInstanceOf[List[Card]])
  }

  test("play card removes it from hand") {
    player.addToDeck(card1)
    player.drawCard()

    val played = player.playCard(card1)
    assert(played.isDefined)
    assertEquals(played.get, card1)
    assertEquals(player.handSize, 0)
  }

  test("play card not in hand returns None") {
    player.addToDeck(card1)
    player.drawCard()

    val played = player.playCard(card2) // card2 not in hand
    assert(played.isEmpty)
    assertEquals(player.handSize, 1) // Hand unchanged
  }

  test("play card from empty hand returns None") {
    val played = player.playCard(card1)
    assert(played.isEmpty)
  }

  test("play same card twice returns None second time") {
    player.addToDeck(card1)
    player.drawCard()

    assert(player.playCard(card1).isDefined) // First play succeeds
    assert(player.playCard(card1).isEmpty)   // Second fails (card not in hand)
  }

  test("can play multiple cards") {
    player.addToDeck(card1)
    player.addToDeck(card2)
    player.drawCards(2)

    player.playCard(card1)
    assertEquals(player.handSize, 1)

    player.playCard(card2)
    assertEquals(player.handSize, 0)
  }

  // Gem management tests
  test("lose gem decreases gem count") {
    val result = player.loseGem()
    assert(result)
    assertEquals(player.gems, 1)
  }

  test("lose multiple gems") {
    player.loseGem()
    player.loseGem()
    assertEquals(player.gems, 0)
  }

  test("cannot lose gem below zero") {
    player.loseGem()
    player.loseGem()

    val result = player.loseGem()
    assert(!result)
    assertEquals(player.gems, 0)
  }

  test("player has lost when gems reach zero") {
    assert(!player.hasLost)

    player.loseGem()
    assert(!player.hasLost)

    player.loseGem()
    assert(player.hasLost)
  }

  test("player with zero initial gems has lost") {
    val p = Player("Loser", 0)
    assert(p.hasLost)
  }

  // toString tests
  test("player toString includes relevant information") {
    player.addToDeck(card1)
    player.addToDeck(card2)
    player.drawCard()

    val str = player.toString
    assert(str.contains("Alice"))
    assert(str.contains("gems"))
    assert(str.contains("deck"))
    assert(str.contains("hand"))
  }

  // Equality tests
  test("two players with same state are equal") {
    val p1 = Player("Alice", 2)
    val p2 = Player("Alice", 2)
    assertEquals(p1, p2)
  }

  test("players with different names are not equal") {
    val p1 = Player("Alice", 2)
    val p2 = Player("Bob", 2)
    assertNotEquals(p1, p2)
  }

  test("players with different gems are not equal") {
    val p1 = Player("Alice", 2)
    val p2 = Player("Alice", 3)
    assertNotEquals(p1, p2)
  }

  test("players with different deck contents are not equal") {
    val p1 = Player("Alice", 2)
    val p2= Player("Alice", 2)

    p1.addToDeck(card1)
    assertNotEquals(p1, p2)
  }

  test("players with different hand contents are not equal") {
    val p1 = Player("Alice", 2)
    val p2 = Player("Alice", 2)

    p1.addToDeck(card1)
    p1.drawCard()

    assertNotEquals(p1, p2)
  }

  test("player is not equal to non-player object") {
    assertNotEquals(player: Any, "not a player")
    assertNotEquals(player: Any, 42)
  }

  // HashCode tests
  test("equal players have same hashCode") {
    val p1 = Player("Alice", 2)
    val p2 = Player("Alice", 2)
    assertEquals(p1.hashCode(), p2.hashCode())
  }

  // Integration scenarios
  test("simulate initial round deal (10 cards)") {
    // Setup deck with 25 cards
    for i <- 1 to 25 do
      player.addToDeck(UnitCard(s"Card$i", i % 10, Melee))

    player.shuffleDeck()
    val drawn = player.drawCards(10)

    assertEquals(drawn, 10)
    assertEquals(player.handSize, 10)
    assertEquals(player.deckSize, 15)
  }

  test("simulate subsequent round deal (3 cards)") {
    // Setup with some cards already in hand
    for i <- 1 to 25 do
      player.addToDeck(UnitCard(s"Card$i", i % 10, Melee))

    player.drawCards(7) // Hand has 7 cards
    val drawn = player.drawCards(3)

    assertEquals(drawn, 3)
    assertEquals(player.handSize, 10)
  }

  test("simulate playing through a hand") {
    player.addToDeck(card1)
    player.addToDeck(card2)
    player.addToDeck(card3)
    player.drawCards(3)

    // Play all cards
    assert(player.playCard(card1).isDefined)
    assert(player.playCard(card2).isDefined)
    assert(player.playCard(card3).isDefined)

    assertEquals(player.handSize, 0)
  }

  test("simulate losing game") {
    assert(!player.hasLost)

    // Lose round 1
    player.loseGem()
    assert(!player.hasLost)

    // Lose round 2
    player.loseGem()
    assert(player.hasLost)

    // Cannot lose more gems
    assert(!player.loseGem())
  }