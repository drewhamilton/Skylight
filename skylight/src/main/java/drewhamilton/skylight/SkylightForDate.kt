package drewhamilton.skylight

import java.time.LocalDate
import java.util.Date
import javax.inject.Inject

/**
 * A convenient [Skylight] wrapper for a single date.
 * TODO: Determine if @Inject internal constructor works with Dagger in another module
 * TODO: Investigate assisted injection
 */
@Suppress("NewApi")
class SkylightForDate @Inject internal constructor(
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
 * Create a [Skylight] wrapper for a constant [Date]
 * @param date The date for which the resulting wrapper will retrieve Skylight info.
 * @return a [SkylightForDate] wrapping the given [Skylight] and [Date]
 */
fun Skylight.forDate(date: LocalDate) = SkylightForDate(this, date)
