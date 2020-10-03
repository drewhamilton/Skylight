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
 * [Skylight] implementations that ignores the coordinates parameter, and instead returns predefined event times for
 * the given date.
 */
sealed class FakeSkylight : Skylight {

    /**
     * Get a predefined [SkylightDay] for the given [date]. [coordinates] are always ignored.
     */
    final override fun getSkylightDay(coordinates: Coordinates, date: LocalDate): SkylightDay = getSkylightDay(date)

    /**
     * Get a predefined [SkylightDay] for the given [date].
     */
    abstract fun getSkylightDay(date: LocalDate): SkylightDay

    /**
     * A [FakeSkylight] that always returns an instance of [SkylightDay.Typical]. Like [SkylightDay.Typical], at least
     * one of [dawn], [sunrise], [sunset], or [dusk] must be non-null.
     *
     * @param zone The time zone used to convert the event times to Instants.
     * @param dawn The time at which the dawn Instant will be calculated in the given time zone for each date.
     * @param sunrise The time at which the sunrise Instant will be calculated in the given time zone for each date.
     * @param sunset The time at which the sunset Instant will be calculated in the given time zone for each date.
     * @param dusk The time at which the dusk Instant will be calculated in the given time zone for each date.
     */
    class Typical(
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
         * Get a [SkylightDay.Typical] for the given [date], with each event [Instant] calculated from the times and
         * time zone passed to this [FakeSkylight]'s constructor.
         */
        override fun getSkylightDay(date: LocalDate): SkylightDay = SkylightDay.Typical(
            date = date,
            dawn = dawn?.toInstant(date),
            sunrise = sunrise?.toInstant(date),
            sunset = sunset?.toInstant(date),
            dusk = dusk?.toInstant(date)
        )

        private fun LocalTime.toInstant(date: LocalDate): Instant = ZonedDateTime.of(date, this, zone).toInstant()
    }

    /**
     * A [FakeSkylight] that always returns an instance of either [SkylightDay.AlwaysDaytime] or
     * [SkylightDay.NeverLight].
     *
     * @param type The type of atypical SkylightDay to return.
     */
    class Atypical(
        private val type: Type
    ) : FakeSkylight() {

        /**
         * Return an instance of either [SkylightDay.AlwaysDaytime] or [SkylightDay.NeverLight] for the given [date].
         * The type of [SkylightDay] returned corresponds to the [Type] passed to this [FakeSkylight]'s constructor.
         */
        override fun getSkylightDay(date: LocalDate): SkylightDay = when (type) {
            Type.AlwaysDaytime -> SkylightDay.AlwaysDaytime(date)
            Type.NeverLight -> SkylightDay.NeverLight(date)
        }

        /**
         * The type of atypical [SkylightDay], corresponding to the non-[SkylightDay.Typical] subclasses of
         * [SkylightDay].
         */
        enum class Type {
            AlwaysDaytime, NeverLight
        }
    }
}
