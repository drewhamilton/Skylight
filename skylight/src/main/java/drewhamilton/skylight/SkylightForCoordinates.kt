package drewhamilton.skylight

import java.util.Date
import javax.inject.Inject

/**
 * A convenient [Skylight] wrapper for a single location.
 */
class SkylightForCoordinates @Inject constructor(
    internal val skylight: Skylight,
    val coordinates: Coordinates
) {
    /**
     * @param date The date for which to return info. The time information in this parameter is ignored.
     * @return [SkylightDay] at this object's coordinates for the given date.
     */
    fun getSkylightDay(date: Date) = skylight.getSkylightDay(coordinates, date)
}

/**
 * @param dateTime The date-time at which to check for lightness.
 * @return Whether it is light outside at the [SkylightForCoordinates]'s coordinates at the given date-time, where
 * "light" means after dawn and before dusk on the given date.
 */
fun SkylightForCoordinates.isLight(dateTime: Date) = skylight.isLight(coordinates, dateTime)

/**
 * @param dateTime The date-time at which to check for darkness.
 * @return Whether it is dark outside at the [SkylightForCoordinates]'s coordinates at the given date-time, where "dark"
 * means before dawn or after dusk on the given date.
 */
fun SkylightForCoordinates.isDark(dateTime: Date) = skylight.isDark(coordinates, dateTime)
