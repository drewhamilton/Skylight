package drewhamilton.skylight

import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.ZonedDateTime
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
    @Deprecated("Replaced by LocalDate-accepting overload")
    fun getSkylightDay(coordinates: Coordinates, date: Date): SkylightDay = SkylightDay.NeverLight

    /**
     * @param coordinates The coordinates to retrieve info for.
     * @param date The date for which to return info.
     * @return [SkylightDay] at the given coordinates for the given date.
     */
    fun getSkylightDay(coordinates: Coordinates, date: LocalDate): NewSkylightDay //=
//        getSkylightDay(coordinates, date.backport())

//    fun getSkylightDay(coordinates: Coordinates, date: org.threeten.bp.LocalDate): SkylightDay
}

/**
 * @param coordinates The coordinates for which lightness should be determined.
 * @param dateTime The date-time at which to check for lightness.
 * @return Whether it is light outside at the given coordinates at the given date-time, where "light" means after dawn
 * and before dusk on the given date.
 */
@Deprecated("Replaced by ZonedDateTime-accepting overload")
fun Skylight.isLight(coordinates: Coordinates, dateTime: Date): Boolean {
    return when (val skylightDay = getSkylightDay(coordinates, dateTime)) {
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
@Deprecated("Replaced by ZonedDateTime-accepting overload")
fun Skylight.isDark(coordinates: Coordinates, dateTime: Date): Boolean = !isLight(coordinates, dateTime)

@Deprecated("Replaced by ZonedDateTime-accepting overload")
private fun isLight(dawn: Date, dusk: Date, dateTime: Date) = dawn.before(dateTime) && dusk.after(dateTime)

/**
 * @param coordinates The coordinates for which lightness should be determined.
 * @param dateTime The date-time at which to check for lightness.
 * @return Whether it is light outside at the given coordinates at the given date-time, where "light" means after dawn
 * and before dusk on the given date.
 */
fun Skylight.isLight(coordinates: Coordinates, dateTime: ZonedDateTime): Boolean {
    return when (val skylightDay = getSkylightDay(coordinates, dateTime.toLocalDate())) {
        is NewSkylightDay.AlwaysDaytime -> true
        is NewSkylightDay.AlwaysLight -> true
        is NewSkylightDay.NeverLight -> false
        is NewSkylightDay.NeverDaytime -> isLight(skylightDay.dawn, skylightDay.dusk, dateTime.toOffsetDateTime().toOffsetTime())
        is NewSkylightDay.Typical -> isLight(skylightDay.dawn, skylightDay.dusk, dateTime.toOffsetDateTime().toOffsetTime())
    }
}

/**
 * @param coordinates The coordinates for which darkness should be determined.
 * @param dateTime The date-time at which to check for darkness.
 * @return Whether it is dark outside at the given coordinates at the given date-time, where "dark" means before dawn
 * or after dusk on the given date.
 */
fun Skylight.isDark(coordinates: Coordinates, dateTime: ZonedDateTime): Boolean = !isLight(coordinates, dateTime)

private fun isLight(dawn: OffsetTime, dusk: OffsetTime, time: OffsetTime) = dawn.isBefore(time) && dusk.isAfter(time)
