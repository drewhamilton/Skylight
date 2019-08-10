package drewhamilton.skylight.backport.calculator

import dagger.Reusable
import drewhamilton.skylight.backport.Coordinates
import drewhamilton.skylight.backport.Skylight
import drewhamilton.skylight.backport.SkylightDay
import drewhamilton.skylight.calculator.EpochMilliSkylightDay
import drewhamilton.skylight.calculator.calculateSkylightInfo
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime
import javax.inject.Inject

/**
 * Adapted from AndroidX's (internal) TwilightCalculator class.
 *
 * Values returned are a bit "fuzzy"; that is, you can get different SkylightInfo for the same location on the same day
 * depending on the exact input time. This difference has been noted to range up to almost 1 minute, but has not been
 * tested extensively. Handle the returned calculation accordingly
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

    private fun noonUtc() = OffsetTime.of(12, 0, 0, 0, ZoneOffset.UTC)
    private fun LocalDate.toNoonUtcEpochMillis() = atTime(noonUtc()).toInstant().toEpochMilli()

    private fun EpochMilliSkylightDay.toSkylightDay(date: LocalDate) = when (this) {
        is EpochMilliSkylightDay.Typical ->
            SkylightDay.Typical(
                dawn.asEpochMilliToUtcDateTime(),
                sunrise.asEpochMilliToUtcDateTime(),
                sunset.asEpochMilliToUtcDateTime(),
                dusk.asEpochMilliToUtcDateTime()
            )
        is EpochMilliSkylightDay.AlwaysDaytime -> SkylightDay.AlwaysDaytime(date)
        is EpochMilliSkylightDay.AlwaysLight ->
            SkylightDay.AlwaysLight(sunrise.asEpochMilliToUtcDateTime(), sunset.asEpochMilliToUtcDateTime())
        is EpochMilliSkylightDay.NeverDaytime ->
            SkylightDay.NeverDaytime(dawn.asEpochMilliToUtcDateTime(), dusk.asEpochMilliToUtcDateTime())
        is EpochMilliSkylightDay.NeverLight -> SkylightDay.NeverLight(date)
    }

    private fun Long.asEpochMilliToUtcDateTime() = ZonedDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneOffset.UTC)
}
