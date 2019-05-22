package drewhamilton.skylight.calculator

/**
 * Represents the Skylight information for a single day at a single location.
 *
 * TODO MISSING What about transition days that e.g. have a sunrise but no sunset?
 */
internal sealed class EpochMilliSkylightDay {

    /**
     * Represents a normal day, where dawn and dusk represent crossing civil twilight, and sunrise and sunset represent
     * crossing the horizon.
     */
    data class Typical(
        val dawn: Long,
        val sunrise: Long,
        val sunset: Long,
        val dusk: Long
    ) : EpochMilliSkylightDay()

    /**
     * Represents a day that is always full light, i.e. the sun never goes below the horizon.
     */
    object AlwaysDaytime : EpochMilliSkylightDay()

    /**
     * Represents a day where there is full light and twilight, but no full darkness, i.e. the sun never goes below
     * civil twilight.
     */
    data class AlwaysLight(
        val sunrise: Long,
        val sunset: Long
    ) : EpochMilliSkylightDay()

    /**
     * Represents a day where there is darkness and twilight, but no full light, i.e. the sun never goes above the
     * horizon.
     */
    data class NeverDaytime(
        val dawn: Long,
        val dusk: Long
    ) : EpochMilliSkylightDay()

    /**
     * Represents a day that is always darkness, i.e. the sun never goes above civil twilight.
     */
    object NeverLight : EpochMilliSkylightDay()

}

