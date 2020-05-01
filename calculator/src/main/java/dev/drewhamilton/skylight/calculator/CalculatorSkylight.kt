package dev.drewhamilton.skylight.calculator

import dagger.Reusable
import dev.drewhamilton.skylight.Coordinates
import dev.drewhamilton.skylight.Skylight
import dev.drewhamilton.skylight.SkylightDay
import java.time.Instant
import java.time.LocalDate
import java.time.OffsetTime
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject

/**
 * Adapted from AndroidX's (internal) TwilightCalculator class.
 */
@Reusable
class CalculatorSkylight @Inject constructor() : Skylight {

    /**
     * Calculates the [SkylightDay] based on the given [coordinates] and [date]. Events in the returned [SkylightDay]
     * are in the given [zoneId].
     */
    override fun getSkylightDay(coordinates: Coordinates, date: LocalDate, zoneId: ZoneId): SkylightDay {
        val epochMillis = date.toNoonUtcEpochMillis(zoneId)
        return calculateSkylightInfo(epochMillis, coordinates.latitude, coordinates.longitude)
            .toSkylightDay(date, zoneId)
    }

    private fun LocalDate.toNoonUtcEpochMillis(zoneId: ZoneId) = atTime(noonToday(zoneId)).toInstant().toEpochMilli()

    private fun noonToday(zoneId: ZoneId) = OffsetTime.of(12, 0, 0, 0, zoneId.rules.getOffset(Instant.now()))

    private fun EpochMilliSkylightDay.toSkylightDay(date: LocalDate, zoneId: ZoneId) = when (this) {
        is EpochMilliSkylightDay.Typical -> SkylightDay.Typical {
            this.date = date
            this.dawn = this@toSkylightDay.dawn.asEpochMilliToDateTime(zoneId)
            this.sunrise = this@toSkylightDay.sunrise.asEpochMilliToDateTime(zoneId)
            this.sunset = this@toSkylightDay.sunset.asEpochMilliToDateTime(zoneId)
            this.dusk = this@toSkylightDay.dusk.asEpochMilliToDateTime(zoneId)
        }
        is EpochMilliSkylightDay.AlwaysDaytime -> SkylightDay.AlwaysDaytime { this.date = date }
        is EpochMilliSkylightDay.NeverLight -> SkylightDay.NeverLight { this.date = date }
    }

    private fun Long?.asEpochMilliToDateTime(zoneId: ZoneId) =
        if (this == null)
            null
        else
            ZonedDateTime.ofInstant(Instant.ofEpochMilli(this), zoneId)
}
