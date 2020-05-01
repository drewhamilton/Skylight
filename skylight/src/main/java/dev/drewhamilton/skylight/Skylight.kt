package dev.drewhamilton.skylight

import java.time.LocalDate
import java.time.ZoneId

/**
 * An interface capable of providing a [SkylightDay] for any given location and date.
 */
interface Skylight {

    /**
     * Get the [SkylightDay] at the given [coordinates] for the given [date]. Any events in the resulting [SkylightDay]
     * will be provided in the [zoneId] time zone.
     */
    fun getSkylightDay(
        coordinates: Coordinates,
        date: LocalDate,
        zoneId: ZoneId = ZoneId.systemDefault()
    ): SkylightDay
}
