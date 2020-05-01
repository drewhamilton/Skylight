package dev.drewhamilton.skylight

import java.time.LocalDate
import java.time.ZoneId

/**
 * A convenient [skylight] wrapper for a single [date].
 */
class SkylightForDate internal constructor(
    private val skylight: Skylight,
    val date: LocalDate
) {
    /**
     * Get a [SkylightDay] for the given [coordinates] and [zoneId] on this object's [date].
     */
    fun getSkylightDay(
        coordinates: Coordinates,
        zoneId: ZoneId = ZoneId.systemDefault()
    ) = skylight.getSkylightDay(coordinates, date, zoneId)
}

/**
 * Create a [SkylightForDate] wrapping the receiver [Skylight] with the given [date].
 */
fun Skylight.forDate(date: LocalDate) = SkylightForDate(this, date)
