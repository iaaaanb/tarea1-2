package model.card

/** Represents a weather card that affects the battlefield.
 *
 * @param name the name of the card
 * @param weatherType the type of weather effect
 */
class WeatherCard(
                   val name: String,
                   val weatherType: WeatherType
                 ) extends Card:

  override def toString: String =
    s"WeatherCard($name, effect=$weatherType)"

  override def equals(obj: Any): Boolean =
    if obj.isInstanceOf[WeatherCard] then
      val other = obj.asInstanceOf[WeatherCard]
      name == other.name && weatherType == other.weatherType
    else false

  override def hashCode(): Int =
    (classOf[WeatherCard], name, weatherType).hashCode()