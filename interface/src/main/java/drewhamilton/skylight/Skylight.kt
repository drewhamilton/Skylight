package drewhamilton.skylight

import drewhamilton.skylight.models.AlwaysDaytime
import drewhamilton.skylight.models.AlwaysLight
import drewhamilton.skylight.models.Coordinates
import drewhamilton.skylight.models.NeverDaytime
import drewhamilton.skylight.models.NeverLight
import drewhamilton.skylight.models.SkylightInfo
import drewhamilton.skylight.models.Typical
import java.util.Date

/**
 * An interface capable of providing [SkylightInfo] for a given location and date-time.
 */
interface Skylight {

    /**
     * @param coordinates The coordinates to retrieve info for.
     * @param date The date for which to return info. The time information in this parameter is ignored.
     * @return [SkylightInfo] at the given coordinates for the given date.
     */
    fun determineSkylightInfo(coordinates: Coordinates, date: Date): SkylightInfo
}

/**
 * @param coordinates The coordinates for which lightness should be determined.
 * @param dateTime The date-time at which to check for lightness.
 * @return Whether it is light outside at the given coordinates at the given date-time, where "light" means after dawn
 * and before dusk on the given date.
 */
fun Skylight.isLight(coordinates: Coordinates, dateTime: Date): Boolean {
    val skylightInfo = determineSkylightInfo(coordinates, dateTime)
    return when (skylightInfo) {
        is AlwaysDaytime -> true
        is AlwaysLight -> true
        is NeverLight -> false
        is NeverDaytime -> isLight(skylightInfo.dawn, skylightInfo.dusk, dateTime)
        is Typical -> isLight(skylightInfo.dawn, skylightInfo.dusk, dateTime)
    }
}

/**
 * @param coordinates The coordinates for which darkness should be determined.
 * @param dateTime The date-time at which to check for darkness.
 * @return Whether it is dark outside at the given coordinates at the given date-time, where "dark" means before dawn
 * or after dusk on the given date.
 */
fun Skylight.isDark(coordinates: Coordinates, dateTime: Date): Boolean = !isLight(coordinates, dateTime)

private fun isLight(dawn: Date, dusk: Date, dateTime: Date) = dawn.before(dateTime) && dusk.after(dateTime)
