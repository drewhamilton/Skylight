package drewhamilton.skylight

import java.time.LocalDate
import java.time.ZonedDateTime

/**
 * An interface capable of providing a [SkylightDay] for any given location and date.
 */
interface Skylight {

    /**
     * Get the [SkylightDay] at the given [coordinates] for the given [date].
     */
    fun getSkylightDay(coordinates: Coordinates, date: LocalDate): SkylightDay
}

/**
 * Determine whether it is light outside at the given [coordinates] at the given [dateTime], where "light" means after
 * dawn and before dusk on the given date.
 */
@Suppress("NewApi")
fun Skylight.isLight(coordinates: Coordinates, dateTime: ZonedDateTime) =
    when (val skylightDay = getSkylightDay(coordinates, dateTime.toLocalDate())) {
        is SkylightDay.AlwaysDaytime -> true
        is SkylightDay.AlwaysLight -> true
        is SkylightDay.NeverLight -> false
        is SkylightDay.NeverDaytime -> isLight(skylightDay.dawn, skylightDay.dusk, dateTime)
        is SkylightDay.Typical -> isLight(skylightDay.dawn, skylightDay.dusk, dateTime)
    }

/**
 * Determine whether it is dark outside at the given [coordinates] at the given [dateTime], where "dark" means before
 * dawn or after dusk on the given date.
 */
fun Skylight.isDark(coordinates: Coordinates, dateTime: ZonedDateTime) = !isLight(coordinates, dateTime)

private fun isLight(dawn: ZonedDateTime, dusk: ZonedDateTime, time: ZonedDateTime) =
    dawn.isBefore(time) && dusk.isAfter(time)
