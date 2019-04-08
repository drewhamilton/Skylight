package drewhamilton.skylight

import java.util.Date

/**
 * An interface capable of providing [SkylightDay] for a given location and date-time.
 */
interface Skylight {

    /**
     * @param coordinates The coordinates to retrieve info for.
     * @param date The date for which to return info. The time information in this parameter is ignored.
     * @return [SkylightDay] at the given coordinates for the given date.
     */
    fun determineSkylightDay(coordinates: Coordinates, date: Date): SkylightDay
}

/**
 * @param coordinates The coordinates for which lightness should be determined.
 * @param dateTime The date-time at which to check for lightness.
 * @return Whether it is light outside at the given coordinates at the given date-time, where "light" means after dawn
 * and before dusk on the given date.
 */
fun Skylight.isLight(coordinates: Coordinates, dateTime: Date): Boolean {
    val skylightDay = determineSkylightDay(coordinates, dateTime)
    return when (skylightDay) {
        is SkylightDay.AlwaysDaytime -> true
        is SkylightDay.AlwaysLight -> true
        is SkylightDay.NeverLight -> false
        is SkylightDay.NeverDaytime -> isLight(skylightDay.dawn, skylightDay.dusk, dateTime)
        is SkylightDay.Typical -> isLight(skylightDay.dawn, skylightDay.dusk, dateTime)
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
