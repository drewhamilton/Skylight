package drewhamilton.skylight.backport

import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetTime
import org.threeten.bp.ZonedDateTime

/**
 * An interface capable of providing a [SkylightDayBackport] for any given location and date.
 */
interface SkylightBackport {

    /**
     * @return [SkylightDayBackport] at the given [coordinates] for the given [date].
     */
    fun getSkylightDay(coordinates: Coordinates, date: LocalDate): SkylightDayBackport
}

/**
 * @return Whether it is light outside at the given [coordinates] at the given [dateTime], where "light" means after
 * dawn and before dusk on the given date.
 */
fun SkylightBackport.isLight(coordinates: Coordinates, dateTime: ZonedDateTime): Boolean {
    return when (val skylightDay = getSkylightDay(coordinates, dateTime.toLocalDate())) {
        is SkylightDayBackport.AlwaysDaytime -> true
        is SkylightDayBackport.AlwaysLight -> true
        is SkylightDayBackport.NeverLight -> false
        is SkylightDayBackport.NeverDaytime ->
            isLight(skylightDay.dawn, skylightDay.dusk, dateTime.toOffsetDateTime().toOffsetTime())
        is SkylightDayBackport.Typical ->
            isLight(skylightDay.dawn, skylightDay.dusk, dateTime.toOffsetDateTime().toOffsetTime())
    }
}

/**
 * @return Whether it is dark outside at the given [coordinates] at the given [dateTime], where "dark" means before dawn
 * or after dusk on the given date.
 */
fun SkylightBackport.isDark(coordinates: Coordinates, dateTime: ZonedDateTime): Boolean =
    !isLight(coordinates, dateTime)

private fun isLight(dawn: OffsetTime, dusk: OffsetTime, time: OffsetTime) = dawn.isBefore(time) && dusk.isAfter(time)
