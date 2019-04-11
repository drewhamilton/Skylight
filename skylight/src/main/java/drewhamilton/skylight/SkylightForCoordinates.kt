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
    fun getSkylightDay(date: Date) = skylight.getSkylightDay(coordinates, date)
}

fun SkylightForCoordinates.isLight(dateTime: Date) = skylight.isLight(coordinates, dateTime)

fun SkylightForCoordinates.isDark(dateTime: Date) = skylight.isDark(coordinates, dateTime)
