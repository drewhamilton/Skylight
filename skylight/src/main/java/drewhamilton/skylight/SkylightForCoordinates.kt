package drewhamilton.skylight

import java.time.LocalDate
import java.time.ZonedDateTime
import javax.inject.Inject

/**
 * A convenient [Skylight] wrapper for a single location.
 */
// TODO: Determine if @Inject internal constructor works with Dagger in another module
// TODO: Investigate assisted injection
class SkylightForCoordinates @Inject internal constructor(
    internal val skylight: Skylight,
    val coordinates: Coordinates
) {
    /**
     * @param date The date for which to return info.
     * @return [SkylightDay] at this object's coordinates for the given date.
     */
    fun getSkylightDay(date: LocalDate) = skylight.getSkylightDay(coordinates, date)
}

/**
 * Create a [Skylight] wrapper for a constant set of [Coordinates]
 * @param coordinates The coordinates for which the resulting wrapper will retrieve Skylight info.
 * @return a [SkylightForCoordinates] wrapping the given [Skylight] and [Coordinates]
 */
fun Skylight.forCoordinates(coordinates: Coordinates) = SkylightForCoordinates(this, coordinates)

/**
 * @param dateTime The date-time at which to check for lightness.
 * @return Whether it is light outside at the [SkylightForCoordinates]'s coordinates at the given date-time, where
 * "light" means after dawn and before dusk on the given date.
 */
fun SkylightForCoordinates.isLight(dateTime: ZonedDateTime) = skylight.isLight(coordinates, dateTime)

/**
 * @param dateTime The date-time at which to check for darkness.
 * @return Whether it is dark outside at the [SkylightForCoordinates]'s coordinates at the given date-time, where "dark"
 * means before dawn or after dusk on the given date.
 */
fun SkylightForCoordinates.isDark(dateTime: ZonedDateTime) = skylight.isDark(coordinates, dateTime)
