package dev.drewhamilton.skylight

import dev.drewhamilton.poko.Poko
import java.time.Instant
import java.time.LocalDate
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
     * Represents a day with at least one "Skylight event"; that is, at least one of [dawn], [sunrise], [sunset], or
     * [dusk]. [dawn] and [dusk] represent the start and end of civil twilight, respectively, and [sunrise] and [sunset]
     * represent the times the sun crosses the horizon.
     *
     * A null value for any of [dawn], [sunrise], [sunset], or [dusk] indicates that that event does not occur on
     * [date]. Different null values have different implications. For example, if only [dawn] and [dusk] are null, the
     * day is always light, but the sun does cross the horizon. If only [sunrise] and [sunset] are null, the sun never
     * crosses above the horizon, but the day does change from dark to light and back. Rarely, 1 or 3 of the values may
     * be null, if the date is a transition day from one sunrise/sunset pattern to another.
     *
     * It can never be the case that all 4 event values are null. In those cases where a day does not have any of these
     * events, either [AlwaysDaytime] or [NeverLight] should be used.
     */
    @Poko class Eventful(
        override val date: LocalDate,
        val dawn: Instant? = null,
        val sunrise: Instant? = null,
        val sunset: Instant? = null,
        val dusk: Instant? = null
    ) : SkylightDay() {

        init {
            require(dawn != null || sunrise != null || sunset != null || dusk != null) {
                "At least one of dawn, sunrise, sunset, or dusk must be non-null"
            }
        }
    }

    /**
     * Represents a day wherein the sun never goes below the horizon.
     */
    @Suppress("EqualsOrHashCode") // Equals is generated
    @Poko class AlwaysDaytime(
        override val date: LocalDate
    ) : SkylightDay() {
        // Constant "1" ensures hash code is unique from NeverLight:
        override fun hashCode() = Objects.hash(1, date)
    }

    /**
     * Represents a day that is always dark, i.e. the sun never goes above the point that marks civil twilight.
     */
    @Suppress("EqualsOrHashCode") // Equals is generated
    @Poko class NeverLight(
        override val date: LocalDate
    ) : SkylightDay() {
        // Constant "2" ensures hash code is unique from AlwaysDaytime:
        override fun hashCode() = Objects.hash(2, date)
    }
}

