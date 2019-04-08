package drewhamilton.skylight

import java.util.Date

/**
 * Represents the Skylight information for a single day at a single location.
 *
 * TODO MISSING What about transition days that e.g. have a sunrise but no sunset?
 */
sealed class SkylightDay {

    /**
     * Represents a normal day, where dawn and dusk represent crossing civil twilight, and sunrise and sunset represent
     * crossing the horizon.
     */
    data class Typical(
        val dawn: Date,
        val sunrise: Date,
        val sunset: Date,
        val dusk: Date
    ) : SkylightDay()

    /**
     * Represents a day that is always full light, i.e. the sun never goes below the horizon.
     */
    object AlwaysDaytime : SkylightDay()

    /**
     * Represents a day where there is full light and twilight, but no full darkness, i.e. the sun never goes below civil
     * twilight.
     */
    data class AlwaysLight(
        val sunrise: Date,
        val sunset: Date
    ) : SkylightDay()

    /**
     * Represents a day where there is darkness and twilight, but no full light, i.e. the sun never goes above the horizon.
     */
    data class NeverDaytime(
        val dawn: Date,
        val dusk: Date
    ) : SkylightDay()

    /**
     * Represents a day that is always darkness, i.e. the sun never goes above civil twilight.
     */
    object NeverLight : SkylightDay()

}

