package dev.drewhamilton.skylight.fake

import dev.drewhamilton.skylight.Coordinates
import dev.drewhamilton.skylight.SkylightDay
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FakeSkylightTest {

    private val testZone = ZoneOffset.UTC
    private val testDawn = LocalTime.of(8, 0)
    private val testSunrise = LocalTime.of(9, 0)
    private val testSunset = LocalTime.of(10, 0)
    private val testDusk = LocalTime.of(11, 0)

    private val typicalFakeSkylight: FakeSkylight =
        FakeSkylight.Typical(testZone, testDawn, testSunrise, testSunset, testDusk)

    @Test fun `Typical getSkylightInfo(Coordinates, LocalDate) returns times on given date`() = runTest {
        val testDate = LocalDate.of(2020, 9, 24)
        val result = typicalFakeSkylight.getSkylightDay(Coordinates(0.0, 0.0), testDate)
        assertTrue(result is SkylightDay.Typical)
        result as SkylightDay.Typical
        assertEquals(ZonedDateTime.of(testDate, testDawn, testZone).toInstant(), result.dawn)
        assertEquals(ZonedDateTime.of(testDate, testSunrise, testZone).toInstant(), result.sunrise)
        assertEquals(ZonedDateTime.of(testDate, testSunset, testZone).toInstant(), result.sunset)
        assertEquals(ZonedDateTime.of(testDate, testDusk, testZone).toInstant(), result.dusk)
    }

    @Test fun `Typical getSkylightInfo(LocalDate) returns times on given date`() = runTest {
        val testDate = LocalDate.ofEpochDay(6343)
        val result = typicalFakeSkylight.getSkylightDay(testDate)
        assertTrue(result is SkylightDay.Typical)
        result as SkylightDay.Typical
        assertEquals(ZonedDateTime.of(testDate, testDawn, testZone).toInstant(), result.dawn)
        assertEquals(ZonedDateTime.of(testDate, testSunrise, testZone).toInstant(), result.sunrise)
        assertEquals(ZonedDateTime.of(testDate, testSunset, testZone).toInstant(), result.sunset)
        assertEquals(ZonedDateTime.of(testDate, testDusk, testZone).toInstant(), result.dusk)
    }

    @Test fun `AlwaysDaytime getSkylightInfo returns AlwaysDaytime`() = runTest {
        val testDate = LocalDate.ofEpochDay(98252)

        val alwaysDaytimeSkylight = FakeSkylight.AlwaysDaytime()
        val result = alwaysDaytimeSkylight.getSkylightDay(testDate)
        assertTrue(result is SkylightDay.AlwaysDaytime)
        assertEquals(testDate, result.date)
    }

    @Test fun `NeverLight getSkylightInfo returns NeverLight`() = runTest {
        val testDate = LocalDate.ofEpochDay(99252)

        val neverLightSkylight = FakeSkylight.NeverLight()
        val result = neverLightSkylight.getSkylightDay(testDate)
        assertTrue(result is SkylightDay.NeverLight)
        assertEquals(testDate, result.date)
    }
}
