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
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime

class CalculatorSkylightTest {

    private val skylight = CalculatorSkylight()

    //region Amsterdam
    @Test fun `Amsterdam on January 6, 2019 is typical`() {
        val result = skylight.getSkylightDay(AMSTERDAM, JANUARY_6_2019, ZoneOffset.UTC)
        assertTrue(result is SkylightDay.Typical)
        result as SkylightDay.Typical
        assertEquals(1546758590702.asEpochMilliToExpectedZonedDateTime(), result.dawn)
        assertEquals(1546761159554.asEpochMilliToExpectedZonedDateTime(), result.sunrise)
        assertEquals(1546789298271.asEpochMilliToExpectedZonedDateTime(), result.sunset)
        assertEquals(1546791867123.asEpochMilliToExpectedZonedDateTime(), result.dusk)
    }
    //endregion

    //region Svalbard
    @Test fun `Svalbard on January 6, 2019 is never light`() {
        val result = skylight.getSkylightDay(SVALBARD, JANUARY_6_2019)
        assertEquals(SkylightDay.NeverLight { date = JANUARY_6_2019 }, result)
    }
    //endregion

    //region Indianapolis
    @Test fun `Indianapolis on July 20, 2019 is typical`() {
        val result = skylight.getSkylightDay(INDIANAPOLIS, JULY_20_2019, ZoneOffset.UTC)

        assertTrue(result is SkylightDay.Typical)
        result as SkylightDay.Typical
        assertEquals(ZonedDateTime.of(2019, 7, 20, 10, 2, 24, 108_000_000, ZoneOffset.UTC), result.dawn)
        assertEquals(ZonedDateTime.of(2019, 7, 20, 10, 35, 14, 739_000_000, ZoneOffset.UTC), result.sunrise)
        assertEquals(ZonedDateTime.of(2019, 7, 21, 1, 8, 52, 914_000_000, ZoneOffset.UTC), result.sunset)
        assertEquals(ZonedDateTime.of(2019, 7, 21, 1, 41, 43, 544_000_000, ZoneOffset.UTC), result.dusk)
    }

    @Test fun `Indianapolis on July 20, 2019 is light at noon`() {
        assertTrue(
            "Expected it to be light in Indianapolis at noon.",
            skylight.isLight(
                INDIANAPOLIS,
                ZonedDateTime.of(JULY_20_2019, LocalTime.NOON, ZoneId.of("America/New_York"))
            )
        )
    }
    //endregion

    private fun Long.asEpochMilliToExpectedZonedDateTime() =
        ZonedDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneOffset.UTC)

    companion object {
        val AMSTERDAM = Coordinates(52.3680, 4.9036)
        val SVALBARD = Coordinates(77.8750, 20.9752)
        val INDIANAPOLIS = Coordinates(39.7684, -86.1581)

        val JANUARY_6_2019: LocalDate = LocalDate.parse("2019-01-06")
        val JULY_20_2019: LocalDate = LocalDate.parse("2019-07-20")
    }
}
