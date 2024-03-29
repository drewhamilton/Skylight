package dev.drewhamilton.skylight.calculator

import dev.drewhamilton.skylight.Coordinates
import dev.drewhamilton.skylight.SkylightDay
import dev.drewhamilton.skylight.isLight
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.Month
import java.time.YearMonth
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CalculatorSkylightTest {

    private val skylight = CalculatorSkylight()

    //region Amsterdam
    @Test fun `Amsterdam on January 6, 2019 is eventful`() = runTest {
        val result = skylight.getSkylightDay(AMSTERDAM, JANUARY_6_2019)
        assertTrue(result is SkylightDay.Eventful)
        result as SkylightDay.Eventful
        assertEquals(Instant.ofEpochMilli(1546758590702), result.dawn)
        assertEquals(Instant.ofEpochMilli(1546761159554), result.sunrise)
        assertEquals(Instant.ofEpochMilli(1546789298271), result.sunset)
        assertEquals(Instant.ofEpochMilli(1546791867123), result.dusk)
    }
    //endregion

    //region Svalbard
    @Test fun `Svalbard on January 6, 2019 is never light`() = runTest {
        val result = skylight.getSkylightDay(SVALBARD, JANUARY_6_2019)
        assertEquals(SkylightDay.NeverLight(date = JANUARY_6_2019), result)
    }

    @Test fun `Svalbard on November 13, 2022`() = runTest {
        val date = LocalDate.of(2022, Month.NOVEMBER, 13)
        val result = skylight.getSkylightDay(SVALBARD, date)
        assertEquals(
            SkylightDay.Eventful(
                date = date,
                dawn = Instant.parse("2022-11-13T09:40:03.476Z"),
                dusk = Instant.parse("2022-11-13T11:03:26.045Z"),
            ),
            result,
        )
    }

    @Test fun `Svalbard every day in 2022`() = runTest {
        val year = 2022
        Month.values().forEach { month ->
            val yearMonth = YearMonth.of(year, month)
            for (day in 1..yearMonth.lengthOfMonth()) {
                val date = LocalDate.of(year, month, day)
                skylight.getSkylightDay(SVALBARD, date)
            }
        }
    }
    //endregion

    //region Indianapolis
    @Test fun `Indianapolis on July 20, 2019 is eventful`() = runTest {
        val result = skylight.getSkylightDay(INDIANAPOLIS, JULY_20_2019)

        assertTrue(result is SkylightDay.Eventful)
        result as SkylightDay.Eventful
        assertEquals(ZonedDateTime.of(2019, 7, 20, 10, 2, 24, 108_000_000, ZoneOffset.UTC).toInstant(), result.dawn)
        assertEquals(ZonedDateTime.of(2019, 7, 20, 10, 35, 14, 739_000_000, ZoneOffset.UTC).toInstant(), result.sunrise)
        assertEquals(ZonedDateTime.of(2019, 7, 21, 1, 8, 52, 914_000_000, ZoneOffset.UTC).toInstant(), result.sunset)
        assertEquals(ZonedDateTime.of(2019, 7, 21, 1, 41, 43, 544_000_000, ZoneOffset.UTC).toInstant(), result.dusk)
    }

    @Test fun `Indianapolis on July 20, 2019 is light at noon`() = runTest {
        assertTrue(
            "Expected it to be light in Indianapolis at noon.",
            skylight.isLight(
                INDIANAPOLIS,
                ZonedDateTime.of(JULY_20_2019, LocalTime.NOON, ZoneId.of("America/New_York")).toInstant()
            )
        )
    }
    //endregion

    companion object {
        val AMSTERDAM = Coordinates(52.3680, 4.9036)
        val SVALBARD = Coordinates(77.8750, 20.9752)
        val INDIANAPOLIS = Coordinates(39.7684, -86.1581)

        val JANUARY_6_2019: LocalDate = LocalDate.parse("2019-01-06")
        val JULY_20_2019: LocalDate = LocalDate.parse("2019-07-20")
    }
}
