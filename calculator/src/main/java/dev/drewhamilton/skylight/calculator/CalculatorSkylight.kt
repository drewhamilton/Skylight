package dev.drewhamilton.skylight.calculator

import dev.drewhamilton.skylight.Coordinates
import dev.drewhamilton.skylight.Skylight
import dev.drewhamilton.skylight.SkylightDay
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.withContext

/**
 * Calculates [SkylightDay]s locally. Adapted from AndroidX's internal TwilightCalculator class.
 *
 * @param coroutineContext Calculations are run in this context. Uses [EmptyCoroutineContext] by default because
 * calculations are fast.
 */
class CalculatorSkylight(
    private val coroutineContext: CoroutineContext = EmptyCoroutineContext,
) : Skylight {

    /**
     * Calculates the [SkylightDay] based on the given [coordinates] and [date].
     */
    override suspend fun getSkylightDay(coordinates: Coordinates, date: LocalDate): SkylightDay {
        return withContext(coroutineContext) {
            val epochMilli = date.toNoonUtcEpochMilli()
            calculateSkylightInfo(epochMilli, coordinates.latitude, coordinates.longitude)
                .toSkylightDay(date)
        }
    }

    private fun LocalDate.toNoonUtcEpochMilli() = atTime(12, 0).toInstant(ZoneOffset.UTC).toEpochMilli()

    private fun EpochMilliSkylightDay.toSkylightDay(date: LocalDate) = when (this) {
        is EpochMilliSkylightDay.Eventful -> SkylightDay.Eventful(
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
