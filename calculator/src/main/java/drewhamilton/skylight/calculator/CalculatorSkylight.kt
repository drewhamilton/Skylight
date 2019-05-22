package drewhamilton.skylight.calculator

import dagger.Reusable
import drewhamilton.skylight.Coordinates
import drewhamilton.skylight.Skylight
import drewhamilton.skylight.SkylightDay
import java.time.Instant
import java.time.LocalDate
import java.time.OffsetTime
import java.time.ZoneOffset
import javax.inject.Inject

/**
 * Adapted from AndroidX's (internal) TwilightCalculator class.
 *
 * Values returned are a bit "fuzzy"; that is, you can get different SkylightInfo for the same location on the same day
 * depending on the exact input time. This difference has been noted to range up to almost 1 minute, but has not been
 * tested extensively. Handle the returned calculation accordingly
 */
@Suppress("NewApi")
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

    private fun noonUtc() = OffsetTime.of(12, 0, 0, 0, ZoneOffset.UTC)
    private fun LocalDate.toNoonUtcEpochMillis() = atTime(noonUtc()).toInstant().toEpochMilli()

    private fun EpochMilliSkylightDay.toSkylightDay(date: LocalDate) = when (this) {
        is EpochMilliSkylightDay.Typical ->
            SkylightDay.Typical(
                date,
                dawn.asEpochMilliToUtcOffsetTime(),
                sunrise.asEpochMilliToUtcOffsetTime(),
                sunset.asEpochMilliToUtcOffsetTime(),
                dusk.asEpochMilliToUtcOffsetTime()
            )
        is EpochMilliSkylightDay.AlwaysDaytime -> SkylightDay.AlwaysDaytime(date)
        is EpochMilliSkylightDay.AlwaysLight ->
            SkylightDay.AlwaysLight(
                date,
                sunrise.asEpochMilliToUtcOffsetTime(),
                sunset.asEpochMilliToUtcOffsetTime()
            )
        is EpochMilliSkylightDay.NeverDaytime ->
            SkylightDay.NeverDaytime(
                date,
                dawn.asEpochMilliToUtcOffsetTime(),
                dusk.asEpochMilliToUtcOffsetTime()
            )
        is EpochMilliSkylightDay.NeverLight -> SkylightDay.NeverLight(date)
    }

    private fun Long.asEpochMilliToUtcOffsetTime() = OffsetTime.ofInstant(Instant.ofEpochMilli(this), ZoneOffset.UTC)
}
