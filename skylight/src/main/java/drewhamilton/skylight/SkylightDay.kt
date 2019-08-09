package drewhamilton.skylight

import java.time.LocalDate
import java.time.ZonedDateTime

/**
 * Represents the Skylight information for a single day at a single location.
 */
// TODO MISSING: What about transition days that e.g. have a sunrise but no sunset?
sealed class SkylightDay {

    /**
     * Represents a normal day, where dawn and dusk represent crossing civil twilight, and sunrise and sunset represent
     * crossing the horizon.
     */
    data class Typical(
        val dawn: ZonedDateTime,
        val sunrise: ZonedDateTime,
        val sunset: ZonedDateTime,
        val dusk: ZonedDateTime
    ) : SkylightDay()

    /**
     * Represents a day that is always full light, i.e. the sun never goes below the horizon.
     */
    data class AlwaysDaytime(
        val date: LocalDate
    ) : SkylightDay()

    /**
     * Represents a day where there is full light and twilight, but no full darkness, i.e. the sun never goes below
     * civil twilight.
     */
    data class AlwaysLight(
        val sunrise: ZonedDateTime,
        val sunset: ZonedDateTime
    ) : SkylightDay()

    /**
     * Represents a day where there is darkness and twilight, but no full light, i.e. the sun never goes above the
     * horizon.
     */
    data class NeverDaytime(
        val dawn: ZonedDateTime,
        val dusk: ZonedDateTime
    ) : SkylightDay()

    /**
     * Represents a day that is always darkness, i.e. the sun never goes above civil twilight.
     */
    data class NeverLight(
        val date: LocalDate
    ) : SkylightDay()

}

