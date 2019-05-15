package drewhamilton.skylight

import java.util.Date
import javax.inject.Inject

/**
 * A convenient [Skylight] wrapper for a single date.
 * TODO: Determine if @Inject internal constructor works with Dagger in another module
 * TODO: Investigate assisted injection
 */
class SkylightForDate @Inject internal constructor(
    private val skylight: Skylight,
    @Deprecated("Replace with LocalDate") val date: Date
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
@Deprecated("Replace with LocalDate-accepting initializer")
fun Skylight.forDate(date: Date) = SkylightForDate(this, date)
