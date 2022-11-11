package dev.drewhamilton.skylight.calculator

/**
 * Represents the Skylight information for a single day at a single location.
 */
internal sealed class EpochMilliSkylightDay {

    /**
     * Represents a normal day, where dawn and dusk represent crossing civil twilight, and sunrise and sunset represent
     * crossing the horizon. Any of the events may be null if they don't occur on the calculated date.
     *
     * It can never be the case that all 4 event values are null. In those cases where a day does not have any of these
     * events, either [AlwaysDaytime] or [NeverLight] should be used.
     */
    data class Eventful(
        val dawn: Long?,
        val sunrise: Long?,
        val sunset: Long?,
        val dusk: Long?
    ) : EpochMilliSkylightDay() {

        init {
            require(dawn != null || sunrise != null || sunset != null || dusk != null) {
                "At least one of dawn, sunrise, sunset, or dusk must be non-null"
            }
        }
    }

    /**
     * Represents a day wherein the sun never goes below the horizon.
     */
    object AlwaysDaytime : EpochMilliSkylightDay()

    /**
     * Represents a day that is always darkness, i.e. the sun never goes above the point that marks civil twilight.
     */
    object NeverLight : EpochMilliSkylightDay()

}

