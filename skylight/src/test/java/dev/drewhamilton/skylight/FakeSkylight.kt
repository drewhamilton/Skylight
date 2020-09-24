package dev.drewhamilton.skylight

import java.time.LocalDate

// TODO: Use DummySkylight if/when possible
class FakeSkylight(
    var skylightDay: SkylightDay
) : Skylight {
    override fun getSkylightDay(coordinates: Coordinates, date: LocalDate): SkylightDay = skylightDay
}
