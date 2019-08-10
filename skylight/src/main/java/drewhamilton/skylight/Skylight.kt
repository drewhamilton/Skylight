package drewhamilton.skylight

import java.time.LocalDate
import java.time.ZonedDateTime

/**
 * An interface capable of providing a [SkylightDay] for any given location and date.
 */
interface Skylight {

    /**
     * @return [SkylightDay] at the given [coordinates] for the given [date].
     */
    fun getSkylightDay(coordinates: Coordinates, date: LocalDate): SkylightDay
}

/**
 * @return Whether it is light outside at the given [coordinates] at the given [dateTime], where "light" means after
 * dawn and before dusk on the given date.
 */
@Suppress("NewApi")
fun Skylight.isLight(coordinates: Coordinates, dateTime: ZonedDateTime): Boolean {
    return when (val skylightDay = getSkylightDay(coordinates, dateTime.toLocalDate())) {
        is SkylightDay.AlwaysDaytime -> true
        is SkylightDay.AlwaysLight -> true
        is SkylightDay.NeverLight -> false
        is SkylightDay.NeverDaytime -> isLight(
            skylightDay.dawn.withZoneSameInstant(dateTime.zone),
            skylightDay.dusk.withZoneSameInstant(dateTime.zone),
            dateTime
        )
        is SkylightDay.Typical -> isLight(
            skylightDay.dawn.withZoneSameInstant(dateTime.zone),
            skylightDay.dusk.withZoneSameInstant(dateTime.zone),
            dateTime
        )
    }
}

/**
 * @return Whether it is dark outside at the given [coordinates] at the given [dateTime], where "dark" means before dawn
 * or after dusk on the given date.
 */
fun Skylight.isDark(coordinates: Coordinates, dateTime: ZonedDateTime): Boolean = !isLight(coordinates, dateTime)

@Suppress("NewApi")
private fun isLight(dawn: ZonedDateTime, dusk: ZonedDateTime, time: ZonedDateTime) =
    dawn.isBefore(time) && dusk.isAfter(time)
