package dev.drewhamilton.skylight.fake

import dev.drewhamilton.skylight.Coordinates
import dev.drewhamilton.skylight.SkylightDay
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import org.junit.Assert
import org.junit.Test

class FakeSkylightTest {

    private val testSkylightDay = SkylightDay.Typical(
        date = LocalDate.of(1970, 1, 1),
        dawn = ZonedDateTime.of(1970, 1, 1, 8, 0, 0, 0, ZoneOffset.UTC).toInstant(),
        sunrise = ZonedDateTime.of(1970, 1, 1, 9, 0, 0, 0, ZoneOffset.UTC).toInstant(),
        sunset = ZonedDateTime.of(1970, 1, 1, 10, 0, 0, 0, ZoneOffset.UTC).toInstant(),
        dusk = ZonedDateTime.of(1970, 1, 1, 11, 0, 0, 0, ZoneOffset.UTC).toInstant()
    )

    private val fakeSkylight: FakeSkylight = FakeSkylight(testSkylightDay)

    @Test fun `getSkylightInfo(Coordinates, LocalDate) returns copy of dummySkylightDay with changed date`() {
        val testDate = LocalDate.of(2020, 9, 24)
        val result = fakeSkylight.getSkylightDay(Coordinates(0.0, 0.0), testDate)
        Assert.assertTrue(result is SkylightDay.Typical)
        result as SkylightDay.Typical
        Assert.assertEquals(testSkylightDay.dawn!!.onDateSameTime(testDate), result.dawn)
        Assert.assertEquals(testSkylightDay.sunrise!!.onDateSameTime(testDate), result.sunrise)
        Assert.assertEquals(testSkylightDay.sunset!!.onDateSameTime(testDate), result.sunset)
        Assert.assertEquals(testSkylightDay.dusk!!.onDateSameTime(testDate), result.dusk)
    }

    @Test fun `getSkylightInfo(LocalDate) returns copy of dummySkylightDay with changed date`() {
        val testDate = LocalDate.ofEpochDay(6343)
        val result = fakeSkylight.getSkylightDay(testDate)
        Assert.assertTrue(result is SkylightDay.Typical)
        result as SkylightDay.Typical
        Assert.assertEquals(testSkylightDay.dawn!!.onDateSameTime(testDate), result.dawn)
        Assert.assertEquals(testSkylightDay.sunrise!!.onDateSameTime(testDate), result.sunrise)
        Assert.assertEquals(testSkylightDay.sunset!!.onDateSameTime(testDate), result.sunset)
        Assert.assertEquals(testSkylightDay.dusk!!.onDateSameTime(testDate), result.dusk)
    }

    private fun Instant.onDateSameTime(date: LocalDate): Instant {
        val dateTime = atOffset(ZoneOffset.UTC)
        val differenceInDays = dateTime.toLocalDate().daysUntil(date)
        return dateTime.plusDays(differenceInDays).toInstant()
    }

    private fun LocalDate.daysUntil(date: LocalDate) = ChronoUnit.DAYS.between(this, date)
}
