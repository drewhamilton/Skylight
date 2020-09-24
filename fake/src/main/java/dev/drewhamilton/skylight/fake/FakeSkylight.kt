package dev.drewhamilton.skylight.fake

import dev.drewhamilton.skylight.Coordinates
import dev.drewhamilton.skylight.Skylight
import dev.drewhamilton.skylight.SkylightDay
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import javax.inject.Inject

/**
 * A [Skylight] implementation that ignores the coordinates parameter, and instead returns a copy of a specific
 * [SkylightDay] for the given date parameter.
 */
class FakeSkylight @Inject constructor(
    private val dummySkylightDay: SkylightDay
) : Skylight {

    /**
     * Get a copy of the [SkylightDay] originally passed to the constructor for the given [date]. [coordinates] are
     * always ignored.
     */
    override fun getSkylightDay(coordinates: Coordinates, date: LocalDate): SkylightDay = getSkylightDay(date)

    /**
     * Get a copy of the [SkylightDay] originally passed to the constructor for the given [date].
     */
    fun getSkylightDay(date: LocalDate): SkylightDay = dummySkylightDay.copy(date)

    private fun SkylightDay.copy(newDate: LocalDate) = when (val original = this) {
        is SkylightDay.Typical -> {
            val daysToAdd = date.daysUntil(newDate)
            SkylightDay.Typical(
                date = newDate,
                dawn = original.dawn?.addDays(daysToAdd),
                sunrise = original.sunrise?.addDays(daysToAdd),
                sunset = original.sunset?.addDays(daysToAdd),
                dusk = original.dusk?.addDays(daysToAdd)
            )
        }
        is SkylightDay.AlwaysDaytime -> SkylightDay.AlwaysDaytime(date = newDate)
        is SkylightDay.NeverLight -> SkylightDay.NeverLight(date = newDate)
    }

    private fun LocalDate.daysUntil(date: LocalDate) = ChronoUnit.DAYS.between(this, date)

    private fun Instant.addDays(days: Long): Instant {
        return atOffset(ZoneOffset.UTC).plusDays(days).toInstant()
    }
}
