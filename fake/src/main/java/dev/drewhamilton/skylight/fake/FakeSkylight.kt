package dev.drewhamilton.skylight.fake

import dev.drewhamilton.skylight.Coordinates
import dev.drewhamilton.skylight.Skylight
import dev.drewhamilton.skylight.SkylightDay
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * [Skylight] implementations that ignore the coordinates parameter and return predefined event times for the given
 * date.
 */
sealed class FakeSkylight : Skylight {

    /**
     * Get a predefined [SkylightDay] for the given [date]. [coordinates] are always ignored.
     */
    final override suspend fun getSkylightDay(coordinates: Coordinates, date: LocalDate): SkylightDay = getSkylightDay(date)

    /**
     * Get a predefined [SkylightDay] for the given [date].
     */
    abstract suspend fun getSkylightDay(date: LocalDate): SkylightDay

    /**
     * A [FakeSkylight] that always returns an instance of [SkylightDay.Eventful]. Like [SkylightDay.Eventful], at least
     * one of [dawn], [sunrise], [sunset], or [dusk] must be non-null.
     *
     * @param zone The time zone used to convert the event times to Instants.
     * @param dawn The time at which the dawn Instant will be calculated in the given time zone for each date.
     * @param sunrise The time at which the sunrise Instant will be calculated in the given time zone for each date.
     * @param sunset The time at which the sunset Instant will be calculated in the given time zone for each date.
     * @param dusk The time at which the dusk Instant will be calculated in the given time zone for each date.
     */
    class Eventful(
        private val zone: ZoneId,
        private val dawn: LocalTime?,
        private val sunrise: LocalTime?,
        private val sunset: LocalTime?,
        private val dusk: LocalTime?,
    ) : FakeSkylight() {
        init {
            require(dawn != null || sunrise != null || sunset != null || dusk != null) {
                "At least one of dawn, sunrise, sunset, or dusk must be non-null"
            }
        }

        /**
         * Get a [SkylightDay.Eventful] for the given [date], with each event [Instant] calculated from the times and
         * time zone passed to this [FakeSkylight]'s constructor.
         */
        override suspend fun getSkylightDay(date: LocalDate): SkylightDay = SkylightDay.Eventful(
            date = date,
            dawn = dawn?.toInstant(date),
            sunrise = sunrise?.toInstant(date),
            sunset = sunset?.toInstant(date),
            dusk = dusk?.toInstant(date)
        )

        private fun LocalTime.toInstant(date: LocalDate): Instant = ZonedDateTime.of(date, this, zone).toInstant()
    }

    /**
     * A [FakeSkylight] that always returns an instance of [SkylightDay.AlwaysDaytime].
     */
    @Suppress("CanSealedSubClassBeObject") // Leave as a class for future compatibility
    class AlwaysDaytime : FakeSkylight() {

        /**
         * Return an instance of [SkylightDay.AlwaysDaytime] for the given [date].
         */
        override suspend fun getSkylightDay(date: LocalDate): SkylightDay = SkylightDay.AlwaysDaytime(date)
    }

    /**
     * A [FakeSkylight] that always returns an instance of [SkylightDay.NeverLight].
     */
    @Suppress("CanSealedSubClassBeObject") // Leave as a class for future compatibility
    class NeverLight : FakeSkylight() {

        /**
         * Return an instance of [SkylightDay.NeverLight] for the given [date].
         */
        override suspend fun getSkylightDay(date: LocalDate): SkylightDay = SkylightDay.NeverLight(date)
    }
}
