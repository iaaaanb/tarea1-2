package model.card

/** Represents the weather effect that a weather card applies.
 *
 * Each weather type corresponds to a specific battlefield effect:
 * - [[Frost]]: Biting Frost (Escarcha mordiente) - sets melee units to strength 1
 * - [[Fog]]: Impenetrable Fog (Niebla impenetrable) - sets ranged units to strength 1
 * - [[Rain]]: Torrential Rain (Lluvia torrencial) - sets siege units to strength 1
 * - [[Clear]]: Clear Weather (Clima despejado) - removes all weather effects
 */
trait WeatherType

/** Biting Frost effect: Sets all melee unit strengths to 1 */
object Frost extends WeatherType

/** Impenetrable Fog effect: Sets all ranged unit strengths to 1 */
object Fog extends WeatherType

/** Torrential Rain effect: Sets all siege unit strengths to 1 */
object Rain extends WeatherType

/** Clear Weather effect: Removes all active weather effects */
object Clear extends WeatherType