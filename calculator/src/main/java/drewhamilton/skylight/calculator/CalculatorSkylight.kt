package drewhamilton.skylight.calculator

import dagger.Reusable
import drewhamilton.skylight.Coordinates
import drewhamilton.skylight.Skylight
import drewhamilton.skylight.SkylightDay
import java.time.Instant
import java.time.LocalDate
import java.time.OffsetTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import javax.inject.Inject

/**
 * Adapted from AndroidX's (internal) TwilightCalculator class.
 *
 * Values returned are a bit "fuzzy"; that is, you can get different SkylightInfo for the same location on the same day
 * depending on the exact input time. This difference has been noted to range up to almost 1 minute, but has not been
 * tested extensively. Handle the returned calculation accordingly.
 */
@Reusable
class CalculatorSkylight @Inject constructor() : Skylight {

    /**
     * Calculates the [SkylightDay] based on the given coordinates and date
     *
     * @param coordinates locations for which to calculate info.
     * @param date date for which to calculate info.
     */
    override fun getSkylightDay(coordinates: Coordinates, date: LocalDate): SkylightDay {
        val epochMillis = date.toNoonUtcEpochMillis()
        return calculateSkylightInfo(epochMillis, coordinates.latitude, coordinates.longitude)
            .toSkylightDay(date)
    }

    private fun LocalDate.toNoonUtcEpochMillis() = atTime(noonUtc()).toInstant().toEpochMilli()
    private fun noonUtc() = OffsetTime.of(12, 0, 0, 0, ZoneOffset.UTC)

    private fun EpochMilliSkylightDay.toSkylightDay(date: LocalDate) = when (this) {
        is EpochMilliSkylightDay.Typical -> SkylightDay.Typical {
            this.date = date
            this.dawn = this@toSkylightDay.dawn.asEpochMilliToUtcDateTime()
            this.sunrise = this@toSkylightDay.sunrise.asEpochMilliToUtcDateTime()
            this.sunset = this@toSkylightDay.sunset.asEpochMilliToUtcDateTime()
            this.dusk = this@toSkylightDay.dusk.asEpochMilliToUtcDateTime()
        }
        is EpochMilliSkylightDay.AlwaysDaytime -> SkylightDay.AlwaysDaytime { this.date = date }
        is EpochMilliSkylightDay.NeverLight -> SkylightDay.NeverLight { this.date = date }
    }

    private fun Long?.asEpochMilliToUtcDateTime() =
        if (this == null)
            null
        else
            ZonedDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneOffset.UTC)
}
