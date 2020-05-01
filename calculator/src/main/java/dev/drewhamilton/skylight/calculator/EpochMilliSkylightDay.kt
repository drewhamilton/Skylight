package dev.drewhamilton.skylight.calculator

/**
 * Represents the Skylight information for a single day at a single location.
 */
internal sealed class EpochMilliSkylightDay {

    /**
     * Represents a normal day, where dawn and dusk represent crossing civil twilight, and sunrise and sunset represent
     * crossing the horizon. Any of the events may be null if they don't occur on the calculated date.
     */
    data class Typical(
        val dawn: Long?,
        val sunrise: Long?,
        val sunset: Long?,
        val dusk: Long?
    ) : EpochMilliSkylightDay()

    /**
     * Represents a day wherein the sun never goes below the horizon.
     */
    object AlwaysDaytime : EpochMilliSkylightDay()

    /**
     * Represents a day that is always darkness, i.e. the sun never goes above civil twilight.
     */
    object NeverLight : EpochMilliSkylightDay()

}

