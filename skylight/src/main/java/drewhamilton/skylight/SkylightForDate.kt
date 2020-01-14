package drewhamilton.skylight

import java.time.LocalDate

/**
 * A convenient [Skylight] wrapper for a single date.
 */
class SkylightForDate internal constructor(
    private val skylight: Skylight,
    val date: LocalDate
) {
    /**
     * @param coordinates The coordinates for which to return info.
     * @return [SkylightDay] on this object's date at the given coordinates.
     */
    fun getSkylightDay(coordinates: Coordinates) = skylight.getSkylightDay(coordinates, date)
}

/**
 * Create a [Skylight] wrapper for a constant [LocalDate]
 * @param date The date for which the resulting wrapper will retrieve Skylight info.
 * @return a [SkylightForDate] wrapping the given [Skylight] and [LocalDate]
 */
fun Skylight.forDate(date: LocalDate) = SkylightForDate(this, date)
