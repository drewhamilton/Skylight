package drewhamilton.skylight.backport

import org.threeten.bp.LocalDate
import org.threeten.bp.ZonedDateTime
import javax.inject.Inject

/**
 * A convenient [SkylightBackport] wrapper for a single location.
 * TODO: Determine if @Inject internal constructor works with Dagger in another module
 * TODO: Investigate assisted injection
 */
class SkylightForCoordinatesBackport @Inject internal constructor(
    internal val skylight: SkylightBackport,
    val coordinates: CoordinatesBackport
) {
    /**
     * @param date The date for which to return info.
     * @return [SkylightDayBackport] at this object's coordinates for the given date.
     */
    fun getSkylightDay(date: LocalDate) = skylight.getSkylightDay(coordinates, date)
}

/**
 * Create a [SkylightBackport] wrapper for a constant set of [CoordinatesBackport]
 * @param coordinates The coordinates for which the resulting wrapper will retrieve Skylight info.
 * @return a [SkylightForCoordinatesBackport] wrapping the given [SkylightBackport] and [CoordinatesBackport]
 */
fun SkylightBackport.forCoordinates(coordinates: CoordinatesBackport) = SkylightForCoordinatesBackport(this, coordinates)

/**
 * @param dateTime The date-time at which to check for lightness.
 * @return Whether it is light outside at the [SkylightForCoordinatesBackport]'s coordinates at the given date-time,
 * where "light" means after dawn and before dusk on the given date.
 */
fun SkylightForCoordinatesBackport.isLight(dateTime: ZonedDateTime) = skylight.isLight(coordinates, dateTime)

/**
 * @param dateTime The date-time at which to check for darkness.
 * @return Whether it is dark outside at the [SkylightForCoordinatesBackport]'s coordinates at the given date-time,
 * where "dark" means before dawn or after dusk on the given date.
 */
fun SkylightForCoordinatesBackport.isDark(dateTime: ZonedDateTime) = skylight.isDark(coordinates, dateTime)
