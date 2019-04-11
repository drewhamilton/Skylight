package drewhamilton.skylight

import java.util.Date
import javax.inject.Inject

/**
 * A convenient [Skylight] wrapper for a single location.
 */
class LocatedSkylight @Inject constructor(
    internal val skylight: Skylight,
    val coordinates: Coordinates
) {
    fun getSkylightDay(date: Date) = skylight.getSkylightDay(coordinates, date)
}

fun LocatedSkylight.isLight(dateTime: Date) = skylight.isLight(coordinates, dateTime)

fun LocatedSkylight.isDark(dateTime: Date) = skylight.isDark(coordinates, dateTime)
