package drewhamilton.skylight.backport.calculator

import dagger.Reusable
import drewhamilton.skylight.backport.Coordinates
import drewhamilton.skylight.backport.SkylightBackport
import drewhamilton.skylight.backport.SkylightDayBackport
import drewhamilton.skylight.calculator.EpochMilliSkylightDay
import drewhamilton.skylight.calculator.calculateSkylightInfo
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetTime
import org.threeten.bp.ZoneOffset
import javax.inject.Inject

/**
 * Adapted from AndroidX's (internal) TwilightCalculator class.
 *
 * Values returned are a bit "fuzzy"; that is, you can get different SkylightInfo for the same location on the same day
 * depending on the exact input time. This difference has been noted to range up to almost 1 minute, but has not been
 * tested extensively. Handle the returned calculation accordingly
 */
@Reusable
class CalculatorSkylightBackport @Inject constructor() : SkylightBackport {

    /**
     * Calculates the [SkylightDayBackport] based on the given coordinates and date
     *
     * @param coordinates locations for which to calculate info.
     * @param date date for which to calculate info.
     */
    override fun getSkylightDay(coordinates: Coordinates, date: LocalDate): SkylightDayBackport {
        val epochMillis = date.toNoonUtcEpochMillis()
        return calculateSkylightInfo(epochMillis, coordinates.latitude, coordinates.longitude)
            .toSkylightDay(date)
    }

    private fun noonUtc() = OffsetTime.of(12, 0, 0, 0, ZoneOffset.UTC)
    private fun LocalDate.toNoonUtcEpochMillis() = atTime(noonUtc()).toInstant().toEpochMilli()

    private fun EpochMilliSkylightDay.toSkylightDay(date: LocalDate) = when (this) {
        is EpochMilliSkylightDay.Typical ->
            SkylightDayBackport.Typical(
                date,
                dawn.asEpochMilliToUtcOffsetTime(),
                sunrise.asEpochMilliToUtcOffsetTime(),
                sunset.asEpochMilliToUtcOffsetTime(),
                dusk.asEpochMilliToUtcOffsetTime()
            )
        is EpochMilliSkylightDay.AlwaysDaytime -> SkylightDayBackport.AlwaysDaytime(date)
        is EpochMilliSkylightDay.AlwaysLight ->
            SkylightDayBackport.AlwaysLight(
                date,
                sunrise.asEpochMilliToUtcOffsetTime(),
                sunset.asEpochMilliToUtcOffsetTime()
            )
        is EpochMilliSkylightDay.NeverDaytime ->
            SkylightDayBackport.NeverDaytime(
                date,
                dawn.asEpochMilliToUtcOffsetTime(),
                dusk.asEpochMilliToUtcOffsetTime()
            )
        is EpochMilliSkylightDay.NeverLight -> SkylightDayBackport.NeverLight(date)
    }

    private fun Long.asEpochMilliToUtcOffsetTime() = OffsetTime.ofInstant(Instant.ofEpochMilli(this), ZoneOffset.UTC)
}
