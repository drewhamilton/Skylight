package drewhamilton.skylight

import drewhamilton.skylight.models.*
import java.util.*

interface SkylightRepository {

    /**
     * @param coordinates The coordinates to retrieve info for.
     * @param date The date for which to return info. The time information in this parameter is ignored.
     * @return [SkylightInfo] at the given coordinates for the given date.
     */
    fun getSkylightInfo(coordinates: Coordinates, date: Date): SkylightInfo
}

/**
 * @param coordinates The coordinates for which lightness should be determined.
 * @param dateTime The date-time at which to check for lightness.
 * @return Whether it is light outside at the given coordinates at the given date-time, where "light" means after dawn
 * and before dusk on the given date.
 */
fun SkylightRepository.isLight(coordinates: Coordinates, dateTime: Date): Boolean {
    val skylightInfo = getSkylightInfo(coordinates, dateTime)
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
fun SkylightRepository.isDark(coordinates: Coordinates, dateTime: Date) = !isLight(coordinates, dateTime)

private fun isLight(dawn: Date, dusk: Date, dateTime: Date) = dawn.before(dateTime) && dusk.after(dateTime)
