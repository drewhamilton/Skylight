package drewhamilton.skylight.calculator

import drewhamilton.skylight.Coordinates
import drewhamilton.skylight.SkylightDay
import drewhamilton.skylight.isLight
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime

class CalculatorSkylightTest {

    private val skylight = CalculatorSkylight()

    //region Amsterdam
    @Test
    fun `Amsterdam on January 6, 2019 is typical`() {
        val result = skylight.getSkylightDay(AMSTERDAM, JANUARY_6_2019)
        assertTrue(result is SkylightDay.Typical)
        result as SkylightDay.Typical
        assertEquals(1546758590702.asEpochMilliToExpectedOffsetTime(), result.dawn)
        assertEquals(1546761159554.asEpochMilliToExpectedOffsetTime(), result.sunrise)
        assertEquals(1546789298271.asEpochMilliToExpectedOffsetTime(), result.sunset)
        assertEquals(1546791867123.asEpochMilliToExpectedOffsetTime(), result.dusk)
    }
    //endregion

    //region Svalbard
    @Test
    fun `Svalbard on January 6, 2019 is never light`() {
        val result = skylight.getSkylightDay(SVALBARD, JANUARY_6_2019)
        assertEquals(SkylightDay.NeverLight(JANUARY_6_2019), result)
    }
    //endregion

    //region Indianapolis
    @Test
    fun `Indianapolis on July 20, 2019 is typical`() {
        val result = skylight.getSkylightDay(INDIANAPOLIS, JULY_20_2019)

        assertTrue(result is SkylightDay.Typical)
        result as SkylightDay.Typical
        assertEquals(OffsetTime.of(10, 2, 24, 108_000_000, ZoneOffset.UTC), result.dawn)
        assertEquals(OffsetTime.of(10, 35, 14, 739_000_000, ZoneOffset.UTC), result.sunrise)
        assertEquals(OffsetTime.of(1, 8, 52, 914_000_000, ZoneOffset.UTC), result.sunset)
        assertEquals(OffsetTime.of(1, 41, 43, 544_000_000, ZoneOffset.UTC), result.dusk)
    }

    @Test
    fun `Indianapolis on July 20, 2019 is light at noon`() {
        assertTrue(
            "Expected it to be light in Indianapolis at noon.",
            skylight.isLight(
                INDIANAPOLIS,
                ZonedDateTime.of(JULY_20_2019, LocalTime.NOON, ZoneId.of("America/New_York"))
            )
        )
    }
    //endregion

    private fun Long.asEpochMilliToExpectedOffsetTime() =
        OffsetTime.ofInstant(Instant.ofEpochMilli(this), ZoneOffset.UTC)

    companion object {
        val AMSTERDAM = Coordinates(52.3680, 4.9036)
        val SVALBARD = Coordinates(77.8750, 20.9752)
        val INDIANAPOLIS = Coordinates(39.7684, -86.1581)

        val JANUARY_6_2019: LocalDate = LocalDate.parse("2019-01-06")
        val JULY_20_2019: LocalDate = LocalDate.parse("2019-07-20")
    }
}
