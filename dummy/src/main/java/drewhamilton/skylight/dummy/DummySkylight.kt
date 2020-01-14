package drewhamilton.skylight.dummy

import drewhamilton.skylight.Coordinates
import drewhamilton.skylight.Skylight
import drewhamilton.skylight.SkylightDay
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

/**
 * A [Skylight] implementation that ignores the coordinates parameter, and instead returns a copy of a specific
 * [SkylightDay] for the given date parameter.
 */
class DummySkylight @Inject constructor(
    private val dummySkylightDay: SkylightDay
) : Skylight {

    /**
     * Get a copy of the [SkylightDay] originally passed to the constructor for the given [date] and [zoneId].
     * [coordinates] are always ignored.
     */
    override fun getSkylightDay(coordinates: Coordinates, date: LocalDate, zoneId: ZoneId): SkylightDay =
        getSkylightDay(date, zoneId)

    /**
     * Get a copy of the [SkylightDay] originally passed to the constructor for the given [date] and [zoneId].
     */
    fun getSkylightDay(date: LocalDate, zoneId: ZoneId = ZoneId.systemDefault()): SkylightDay =
        dummySkylightDay.copy(date, zoneId)

    private fun SkylightDay.copy(newDate: LocalDate, zoneId: ZoneId) = when (val original = this) {
        is SkylightDay.Typical -> {
            val daysToAdd = this.date.daysUntil(newDate)
            SkylightDay.Typical {
                date = newDate
                dawn = original.dawn?.addDays(daysToAdd)?.withZoneSameInstant(zoneId)
                sunrise = original.sunrise?.addDays(daysToAdd)?.withZoneSameInstant(zoneId)
                sunset = original.sunset?.addDays(daysToAdd)?.withZoneSameInstant(zoneId)
                dusk = original.dusk?.addDays(daysToAdd)?.withZoneSameInstant(zoneId)
            }
        }
        is SkylightDay.AlwaysDaytime -> SkylightDay.AlwaysDaytime { date = newDate }
        is SkylightDay.NeverLight -> SkylightDay.NeverLight { date = newDate }
    }

    private fun LocalDate.daysUntil(date: LocalDate) = ChronoUnit.DAYS.between(this, date)

    private fun ZonedDateTime.addDays(days: Long): ZonedDateTime {
        val date = toLocalDate().plusDays(days)
        return ZonedDateTime.of(date, toLocalTime(), this.zone)
    }
}
