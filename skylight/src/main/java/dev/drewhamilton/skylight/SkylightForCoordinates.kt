package dev.drewhamilton.skylight

import java.time.LocalDate

/**
 * A convenient [skylight] wrapper for a single set of [coordinates].
 */
class SkylightForCoordinates internal constructor(
    internal val skylight: Skylight,
    val coordinates: Coordinates
) {
    /**
     * Get a [SkylightDay] for the given [date] at this object's [coordinates].
     */
    suspend fun getSkylightDay(date: LocalDate) = skylight.getSkylightDay(coordinates, date)
}

/**
 * Create a [SkylightForCoordinates] wrapping the receiver [Skylight] with the given [coordinates].
 */
fun Skylight.forCoordinates(
    coordinates: Coordinates,
) = SkylightForCoordinates(this, coordinates)
