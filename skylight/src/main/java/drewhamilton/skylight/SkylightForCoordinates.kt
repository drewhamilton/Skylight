package drewhamilton.skylight

import java.time.LocalDate
import java.time.ZoneId

/**
 * A convenient [skylight] wrapper for a single set of [coordinates] and [zoneId].
 */
class SkylightForCoordinates internal constructor(
    internal val skylight: Skylight,
    val coordinates: Coordinates,
    val zoneId: ZoneId = ZoneId.systemDefault()
) {
    /**
     * Get a [SkylightDay] for the given [date] at this object's [coordinates] and [zoneId].
     */
    fun getSkylightDay(date: LocalDate) = skylight.getSkylightDay(coordinates, date, zoneId)
}

/**
 * Create a [SkylightForCoordinates] wrapping the receiver [Skylight] with the given [coordinates] and [zoneId].
 */
fun Skylight.forCoordinates(
    coordinates: Coordinates,
    zoneId: ZoneId = ZoneId.systemDefault()
) = SkylightForCoordinates(this, coordinates, zoneId)
