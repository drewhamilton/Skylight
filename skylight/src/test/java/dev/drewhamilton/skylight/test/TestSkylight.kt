package dev.drewhamilton.skylight.test

import dev.drewhamilton.skylight.Coordinates
import dev.drewhamilton.skylight.Skylight
import dev.drewhamilton.skylight.SkylightDay
import java.time.LocalDate

class TestSkylight(
    private val skylightDays: Map<Pair<Coordinates, LocalDate>, SkylightDay>
) : Skylight {

    constructor(vararg pairs: Pair<Pair<Coordinates, LocalDate>, SkylightDay>) : this(mapOf(*pairs))

    override fun getSkylightDay(coordinates: Coordinates, date: LocalDate): SkylightDay {
        val result = skylightDays[coordinates to date]
        return requireNotNull(result) { "No SkylightDay found for {$coordinates, $date}" }
    }
}
