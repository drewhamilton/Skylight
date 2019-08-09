package drewhamilton.skylight.backport.dummy

import drewhamilton.skylight.backport.Coordinates
import drewhamilton.skylight.backport.Skylight
import drewhamilton.skylight.backport.SkylightDay
import org.threeten.bp.LocalDate
import org.threeten.bp.ZonedDateTime
import javax.inject.Inject

/**
 * A [Skylight] implementation that ignores the coordinates parameter, and instead returns a copy of a specific
 * [SkylightDay] for the given date parameter.
 */
class DummySkylight @Inject constructor(
    private val dummySkylightDay: SkylightDay
) : Skylight {

    /**
     * Get a copy of the [SkylightDay] originally passed to the constructor for the given [date]. [coordinates] are
     * always ignored.
     */
    override fun getSkylightDay(coordinates: Coordinates, date: LocalDate) = getSkylightDay(date)

    /**
     * Get a copy of the [SkylightDay] originally passed to the constructor for the given [date].
     */
    fun getSkylightDay(date: LocalDate) = dummySkylightDay.copy(date)

    private fun SkylightDay.copy(date: LocalDate): SkylightDay {
        return when (this) {
            is SkylightDay.Typical -> {
                val daysToAdd = dawn.toLocalDate().daysUntil(date)
                copy(
                    dawn = dawn.addDays(daysToAdd),
                    sunrise = sunrise.addDays(daysToAdd),
                    sunset = sunset.addDays(daysToAdd),
                    dusk = dusk.addDays(daysToAdd)
                )
            }
            is SkylightDay.AlwaysDaytime -> copy(date = date)
            is SkylightDay.AlwaysLight -> {
                val daysToAdd = sunrise.toLocalDate().daysUntil(date)
                copy(
                    sunrise = sunrise.addDays(daysToAdd),
                    sunset = sunset.addDays(daysToAdd)
                )
            }
            is SkylightDay.NeverDaytime -> {
                val daysToAdd = dawn.toLocalDate().daysUntil(date)
                copy(
                    dawn = dawn.addDays(daysToAdd),
                    dusk = dusk.addDays(daysToAdd)
                )
            }
            is SkylightDay.NeverLight -> copy(date = date)
        }
    }

    private fun LocalDate.daysUntil(date: LocalDate) = this.until(date).days

    private fun ZonedDateTime.addDays(days: Int): ZonedDateTime {
        val date = toLocalDate().plusDays(days.toLong())
        return ZonedDateTime.of(date, toLocalTime(), this.zone)
    }
}
