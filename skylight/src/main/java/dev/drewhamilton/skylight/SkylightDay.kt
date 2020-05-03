package dev.drewhamilton.skylight

import dev.drewhamilton.extracare.DataApi
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.Objects

/**
 * Represents the Skylight information for a single day at a single location.
 */
sealed class SkylightDay {

    /**
     * The date represented by the [SkylightDay].
     */
    abstract val date: LocalDate

    /**
     * Represents a normal day, where dawn and dusk represent crossing civil twilight, and sunrise and sunset represent
     * crossing the horizon.
     *
     * A null value for [dawn], [sunrise], [sunset], or [dusk] indicate that that event does not occur on that [date].
     * Different null values have different implications. For example, if only [dawn] and [dusk] are null, the day is
     * always light, but the sun does cross the horizon. If only [sunrise] and [sunset] are null, the sun never crosses
     * above the horizon, but the day does change from dark to light and back. Rarely, 1 or 3 of the values may be null,
     * if the date is a transition day from one sunrise/sunset pattern to another.
     *
     * It should never be the case that all 4 event values are null. In those cases where a day does not have any of
     * these events, either [AlwaysDaytime] or [NeverLight] should be used.
     */
    @DataApi class Typical private constructor(
        override val date: LocalDate,
        val dawn: ZonedDateTime?,
        val sunrise: ZonedDateTime?,
        val sunset: ZonedDateTime?,
        val dusk: ZonedDateTime?
    ) : SkylightDay() {

        class Builder {
            @set:JvmSynthetic
            var date: LocalDate? = null

            @set:JvmSynthetic
            var dawn: ZonedDateTime? = null

            @set:JvmSynthetic
            var sunrise: ZonedDateTime? = null

            @set:JvmSynthetic
            var sunset: ZonedDateTime? = null

            @set:JvmSynthetic
            var dusk: ZonedDateTime? = null

            fun setDate(date: LocalDate?) = apply { this.date = date }
            fun setDawn(dawn: ZonedDateTime?) = apply { this.dawn = dawn }
            fun setSunrise(sunrise: ZonedDateTime?) = apply { this.sunrise = sunrise }
            fun setSunset(sunset: ZonedDateTime?) = apply { this.sunset = sunset }
            fun setDusk(dusk: ZonedDateTime?) = apply { this.dusk = dusk }

            fun build(): Typical {
                val date = checkNotNull(date) { "All SkylightDays must have a date" }
                check(!(dawn == null && sunrise == null && sunset == null && dusk == null)) {
                    "At least one of dawn, sunrise, sunset, and dusk must be non-null"
                }
                return Typical(date, dawn, sunrise, sunset, dusk)
            }
        }
    }

    /**
     * Represents a day wherein the sun never goes below the horizon.
     */
    @Suppress("EqualsOrHashCode") // Equals is generated
    @DataApi class AlwaysDaytime private constructor(
        override val date: LocalDate
    ) : SkylightDay() {
        // Constant "1" ensures hash code is unique from NeverLight:
        override fun hashCode() = Objects.hash(1, date)

        class Builder {
            @set:JvmSynthetic
            var date: LocalDate? = null

            fun setDate(date: LocalDate?) = apply { this.date = date }

            fun build(): AlwaysDaytime {
                val date = checkNotNull(date) { "All SkylightDays must have a date" }
                return AlwaysDaytime(date)
            }
        }
    }

    /**
     * Represents a day that is always dark, i.e. the sun never goes above civil twilight.
     */
    @Suppress("EqualsOrHashCode") // Equals is generated
    @DataApi class NeverLight private constructor(
        override val date: LocalDate
    ) : SkylightDay() {
        // Constant "2" ensures hash code is unique from AlwaysDaytime:
        override fun hashCode() = Objects.hash(2, date)

        class Builder {
            @set:JvmSynthetic
            var date: LocalDate? = null

            fun setDate(date: LocalDate?) = apply { this.date = date }

            fun build(): NeverLight {
                val date = checkNotNull(date) { "All SkylightDays must have a date" }
                return NeverLight(date)
            }
        }
    }

    companion object {
        @Suppress("FunctionName") // Initializer DSL pattern
        fun Typical(initializer: Typical.Builder.() -> Unit): Typical = Typical.Builder()
            .apply(initializer)
            .build()

        @Suppress("FunctionName") // Initializer DSL pattern
        fun AlwaysDaytime(initializer: AlwaysDaytime.Builder.() -> Unit): AlwaysDaytime = AlwaysDaytime.Builder()
            .apply(initializer)
            .build()

        @Suppress("FunctionName") // Initializer DSL pattern
        fun NeverLight(initializer: NeverLight.Builder.() -> Unit): NeverLight = NeverLight.Builder()
            .apply(initializer)
            .build()
    }
}

