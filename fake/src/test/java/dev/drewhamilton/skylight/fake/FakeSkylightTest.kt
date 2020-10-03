package dev.drewhamilton.skylight.fake

import dev.drewhamilton.skylight.Coordinates
import dev.drewhamilton.skylight.SkylightDay
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FakeSkylightTest {

    private val expectedTypicalSkylightDay = SkylightDay.Typical(
        date = LocalDate.of(1970, 1, 1),
        dawn = ZonedDateTime.of(1970, 1, 1, 8, 0, 0, 0, ZoneOffset.UTC).toInstant(),
        sunrise = ZonedDateTime.of(1970, 1, 1, 9, 0, 0, 0, ZoneOffset.UTC).toInstant(),
        sunset = ZonedDateTime.of(1970, 1, 1, 10, 0, 0, 0, ZoneOffset.UTC).toInstant(),
        dusk = ZonedDateTime.of(1970, 1, 1, 11, 0, 0, 0, ZoneOffset.UTC).toInstant()
    )

    private val testZone = ZoneOffset.UTC
    private val testDawn = LocalTime.of(8, 0)
    private val testSunrise = LocalTime.of(9, 0)
    private val testSunset = LocalTime.of(10, 0)
    private val testDusk = LocalTime.of(11, 0)

    private val testDate = LocalDate.of(1970, 1, 1)

    private val typicalFakeSkylight: FakeSkylight =
        FakeSkylight.Typical(testZone, testDawn, testSunrise, testSunset, testDusk)

    @Test fun `Typical getSkylightInfo(Coordinates, LocalDate) returns times on given date`() {
        val testDate = LocalDate.of(2020, 9, 24)
        val result = typicalFakeSkylight.getSkylightDay(Coordinates(0.0, 0.0), testDate)
        assertTrue(result is SkylightDay.Typical)
        result as SkylightDay.Typical
        assertEquals(ZonedDateTime.of(testDate, testDawn, testZone).toInstant(), result.dawn)
        assertEquals(ZonedDateTime.of(testDate, testSunrise, testZone).toInstant(), result.sunrise)
        assertEquals(ZonedDateTime.of(testDate, testSunset, testZone).toInstant(), result.sunset)
        assertEquals(ZonedDateTime.of(testDate, testDusk, testZone).toInstant(), result.dusk)
    }

    @Test fun `Typical getSkylightInfo(LocalDate) returns times on given date`() {
        val testDate = LocalDate.ofEpochDay(6343)
        val result = typicalFakeSkylight.getSkylightDay(testDate)
        assertTrue(result is SkylightDay.Typical)
        result as SkylightDay.Typical
        assertEquals(ZonedDateTime.of(testDate, testDawn, testZone).toInstant(), result.dawn)
        assertEquals(ZonedDateTime.of(testDate, testSunrise, testZone).toInstant(), result.sunrise)
        assertEquals(ZonedDateTime.of(testDate, testSunset, testZone).toInstant(), result.sunset)
        assertEquals(ZonedDateTime.of(testDate, testDusk, testZone).toInstant(), result.dusk)
    }

    @Test fun `Atypical getSkylightInfo returns instance corresponding to type`() {
        val testDate = LocalDate.ofEpochDay(98252)

        val alwaysDaytimeSkylight = FakeSkylight.Atypical(FakeSkylight.Atypical.Type.AlwaysDaytime)
        val result1 = alwaysDaytimeSkylight.getSkylightDay(testDate)
        assertTrue(result1 is SkylightDay.AlwaysDaytime)
        assertEquals(testDate, result1.date)

        val neverLightSkylight = FakeSkylight.Atypical(FakeSkylight.Atypical.Type.NeverLight)
        val result2 = neverLightSkylight.getSkylightDay(testDate)
        assertTrue(result2 is SkylightDay.NeverLight)
        assertEquals(testDate, result2.date)
    }
}
