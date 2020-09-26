package dev.drewhamilton.skylight.calculator

import dev.drewhamilton.skylight.Coordinates
import dev.drewhamilton.skylight.Skylight
import dev.drewhamilton.skylight.SkylightDay
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

/**
 * Adapted from AndroidX's (internal) TwilightCalculator class.
 */
class CalculatorSkylight : Skylight {

    /**
     * Calculates the [SkylightDay] based on the given [coordinates] and [date].
     */
    override fun getSkylightDay(coordinates: Coordinates, date: LocalDate): SkylightDay {
        val epochMilli = date.toNoonUtcEpochMilli()
        return calculateSkylightInfo(epochMilli, coordinates.latitude, coordinates.longitude)
            .toSkylightDay(date)
    }

    private fun LocalDate.toNoonUtcEpochMilli() = atTime(12, 0).toInstant(ZoneOffset.UTC).toEpochMilli()

    private fun EpochMilliSkylightDay.toSkylightDay(date: LocalDate) = when (this) {
        is EpochMilliSkylightDay.Typical -> SkylightDay.Typical(
            date = date,
            dawn = dawn.asEpochMilliToInstant(),
            sunrise = sunrise.asEpochMilliToInstant(),
            sunset = sunset.asEpochMilliToInstant(),
            dusk = dusk.asEpochMilliToInstant()
        )
        is EpochMilliSkylightDay.AlwaysDaytime -> SkylightDay.AlwaysDaytime(date = date)
        is EpochMilliSkylightDay.NeverLight -> SkylightDay.NeverLight(date = date)
    }

    private fun Long?.asEpochMilliToInstant(): Instant? = if (this == null) null else Instant.ofEpochMilli(this)
}
