package model.player

import model.card.Card
import scala.collection.mutable

/** Represents a player in the game.
 *
 * Each player has a name, a number of gems (representing lives),
 * a deck of cards, and a hand of up to 10 cards. Players can
 * draw cards from their deck, play cards from their hand, and
 * lose gems when losing rounds.
 *
 * @param name the player's name (must be non-empty)
 * @param initialGems the starting number of gems (must be >= 0, default 2)
 */
class Player(val name: String, initialGems: Int = 2):
  require(initialGems >= 0, "Initial gems cannot be negative")
  require(name.nonEmpty, "Player name cannot be empty")

  private var _gems: Int = initialGems
  private val _deck: mutable.ListBuffer[Card] = mutable.ListBuffer.empty
  private val _hand: mutable.ListBuffer[Card] = mutable.ListBuffer.empty

  /** Returns the current number of gems */
  def gems: Int = _gems

  /** Returns a copy of the current hand to prevent external modification */
  def hand: List[Card] = _hand.toList

  /** Returns the number of cards in the deck */
  def deckSize: Int = _deck.size

  /** Returns the number of cards in hand */
  def handSize: Int = _hand.size

  /** Adds a card to the bottom of the deck.
   *
   * @param card the card to add
   */
  def addToDeck(card: Card): Unit =
    _deck.addOne(card)

  /** Shuffles the deck randomly */
  def shuffleDeck(): Unit =
    val shuffled = scala.util.Random.shuffle(_deck)
    _deck.clear()
    _deck.addAll(shuffled)

  /** Draws a card from the top of the deck and adds it to hand.
   * Respects the 10-card hand limit.
   *
   * @return Some(card) if successful, None if deck is empty or hand is full
   */
  def drawCard(): Option[Card] =
    if _deck.isEmpty then
      None
    else if _hand.size >= 10 then
      None
    else
      val card = _deck.remove(0)
      _hand.addOne(card)
      Some(card)

  /** Draws multiple cards from the deck.
   *
   * @param count the number of cards to draw (must be >= 0)
   * @return the number of cards actually drawn
   */
  def drawCards(count: Int): Int =
    require(count >= 0, "Cannot draw negative number of cards")
    var drawn = 0
    for _ <- 0 until count do
      if drawCard().isDefined then
        drawn += 1
    drawn

  /** Attempts to play a card from hand.
   * The card is removed from hand if it exists.
   *
   * @param card the card to play
   * @return Some(card) if the card was in hand, None otherwise
   */
  def playCard(card: Card): Option[Card] =
    val index = _hand.indexOf(card)
    if index >= 0 then
      _hand.remove(index)
      Some(card)
    else
      None

  /** Removes a gem from the player.
   * Used when losing a round.
   *
   * @return true if a gem was removed, false if already at 0
   */
  def loseGem(): Boolean =
    if _gems > 0 then
      _gems -= 1
      true
    else
      false

  /** Checks if the player has lost (0 gems remaining) */
  def hasLost: Boolean = _gems == 0

  override def toString: String =
    s"Player($name, gems=$_gems, deck=${_deck.size}, hand=${_hand.size})"

  override def equals(obj: Any): Boolean =
    if obj.isInstanceOf[Player] then
      val other = obj.asInstanceOf[Player]
      name == other.name &&
        _gems == other._gems &&
        _deck == other._deck &&
        _hand == other._hand
    else false

  override def hashCode(): Int =
    (classOf[Player], name, _gems, _deck, _hand).hashCode()