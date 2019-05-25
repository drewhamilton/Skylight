package drewhamilton.skylight.backport

import org.threeten.bp.LocalDate
import javax.inject.Inject

/**
 * A convenient [SkylightBackport] wrapper for a single date.
 * TODO: Determine if @Inject internal constructor works with Dagger in another module
 * TODO: Investigate assisted injection
 */
class SkylightForDateBackport @Inject internal constructor(
    private val skylight: SkylightBackport,
    val date: LocalDate
) {
    /**
     * @param coordinates The coordinates for which to return info.
     * @return [SkylightDayBackport] on this object's date at the given coordinates.
     */
    fun getSkylightDay(coordinates: CoordinatesBackport) = skylight.getSkylightDay(coordinates, date)
}

/**
 * Create a [SkylightBackport] wrapper for a constant [LocalDate]
 * @param date The date for which the resulting wrapper will retrieve Skylight info.
 * @return a [SkylightForDateBackport] wrapping the given [SkylightBackport] and [LocalDate]
 */
fun SkylightBackport.forDate(date: LocalDate) = SkylightForDateBackport(this, date)
