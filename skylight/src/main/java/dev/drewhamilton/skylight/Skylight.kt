package dev.drewhamilton.skylight

import java.time.LocalDate

/**
 * An interface capable of providing a [SkylightDay] for any given location and date.
 */
interface Skylight {

    /**
     * Get the [SkylightDay] at the given [coordinates] for the given [date].
     */
    suspend fun getSkylightDay(
        coordinates: Coordinates,
        date: LocalDate
    ): SkylightDay
}
