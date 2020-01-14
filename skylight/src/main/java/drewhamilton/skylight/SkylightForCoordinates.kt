package drewhamilton.skylight

import java.time.LocalDate

/**
 * A convenient [Skylight] wrapper for a single location.
 */
class SkylightForCoordinates internal constructor(
    internal val skylight: Skylight,
    val coordinates: Coordinates
) {
    /**
     * @param date The date for which to return info.
     * @return [SkylightDay] at this object's coordinates for the given date.
     */
    fun getSkylightDay(date: LocalDate) = skylight.getSkylightDay(coordinates, date)
}

/**
 * Create a [Skylight] wrapper for a constant set of [Coordinates]
 * @param coordinates The coordinates for which the resulting wrapper will retrieve Skylight info.
 * @return a [SkylightForCoordinates] wrapping the given [Skylight] and [Coordinates]
 */
fun Skylight.forCoordinates(coordinates: Coordinates) = SkylightForCoordinates(this, coordinates)
