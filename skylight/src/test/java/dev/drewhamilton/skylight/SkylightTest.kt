package dev.drewhamilton.skylight

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate
import java.time.Month
import java.time.ZoneOffset
import java.time.ZonedDateTime

class SkylightTest {

    private val testCoordinates = Coordinates(50.0, 60.0)
    private val testDate = LocalDate.of(2019, Month.MAY, 15)
    private val testDawn = ZonedDateTime.of(2019, 5, 15, 8, 0, 0, 0, ZoneOffset.UTC)
    private val testSunrise = ZonedDateTime.of(2019, 5, 15, 10, 0, 0, 0, ZoneOffset.UTC)
    private val testSunset = ZonedDateTime.of(2019, 5, 15, 14, 0, 0, 0, ZoneOffset.UTC)
    private val testDusk = ZonedDateTime.of(2019, 5, 15, 16, 0, 0, 0, ZoneOffset.UTC)

    private val beforeDawn = ZonedDateTime.of(testDate, testDawn.minusHours(2).toLocalTime(), ZoneOffset.UTC)
    private val betweenDawnAndDusk = ZonedDateTime.of(testDate, testSunrise.plusHours(2).toLocalTime(), ZoneOffset.UTC)
    private val afterDusk = ZonedDateTime.of(testDate, testDusk.plusHours(2).toLocalTime(), ZoneOffset.UTC)

    private val testAlwaysDaytime = SkylightDay.AlwaysDaytime(date = testDate)
    private val testAlwaysLight = SkylightDay.Typical(
        date = testDate,
        sunrise = testSunrise.toInstant(),
        sunset = testSunset.toInstant()
    )
    private val testNeverLight = SkylightDay.NeverLight(date = testDate)
    private val testNeverDaytime = SkylightDay.Typical(
        date = testDate,
        dawn = testDawn.toInstant(),
        dusk = testDusk.toInstant()
    )
    private val testTypical = SkylightDay.Typical (
        date = testDate,
        dawn = testDawn.toInstant(),
        sunrise = testSunrise.toInstant(),
        sunset = testSunset.toInstant(),
        dusk = testDusk.toInstant()
    )

    //region isLight
    @Test fun `isLight with AlwaysDaytime returns true`() {
        val skylight = FakeSkylight(testAlwaysDaytime)

        assertTrue(skylight.isLight(testCoordinates, beforeDawn))
        assertTrue(skylight.isLight(testCoordinates, betweenDawnAndDusk))
        assertTrue(skylight.isLight(testCoordinates, afterDusk))
    }

    @Test fun `isLight with AlwaysLight returns true`() {
        val skylight = FakeSkylight(testAlwaysLight)

        assertTrue(skylight.isLight(testCoordinates, beforeDawn))
        assertTrue(skylight.isLight(testCoordinates, betweenDawnAndDusk))
        assertTrue(skylight.isLight(testCoordinates, afterDusk))
    }

    @Test fun `isLight with NeverLight returns false`() {
        val skylight = FakeSkylight(testNeverLight)

        assertFalse(skylight.isLight(testCoordinates, beforeDawn))
        assertFalse(skylight.isLight(testCoordinates, betweenDawnAndDusk))
        assertFalse(skylight.isLight(testCoordinates, afterDusk))
    }

    @Test fun `isLight with NeverDaytime returns false before dawn`() {
        val skylight = FakeSkylight(testNeverDaytime)

        assertFalse(skylight.isLight(testCoordinates, beforeDawn))
    }

    @Test fun `isLight with NeverDaytime returns true between dawn and dusk`() {
        val skylight = FakeSkylight(testNeverDaytime)

        assertTrue(skylight.isLight(testCoordinates, betweenDawnAndDusk))
    }

    @Test fun `isLight with NeverDaytime returns false after dusk`() {
        val skylight = FakeSkylight(testNeverDaytime)

        assertFalse(skylight.isLight(testCoordinates, afterDusk))
    }

    @Test fun `isLight with Typical returns false before dawn`() {
        val skylight = FakeSkylight(testTypical)

        assertFalse(skylight.isLight(testCoordinates, beforeDawn))
    }

    @Test fun `isLight with Typical returns true between dawn and dusk`() {
        val skylight = FakeSkylight(testTypical)

        assertTrue(skylight.isLight(testCoordinates, betweenDawnAndDusk))
    }

    @Test fun `isLight with Typical returns false after dusk`() {
        val skylight = FakeSkylight(testTypical)

        assertFalse(skylight.isLight(testCoordinates, afterDusk))
    }
    //endregion

    //region isDark
    @Test fun `isDark with AlwaysDaytime returns false`() {
        val skylight = FakeSkylight(testAlwaysDaytime)

        assertFalse(skylight.isDark(testCoordinates, beforeDawn))
        assertFalse(skylight.isDark(testCoordinates, betweenDawnAndDusk))
        assertFalse(skylight.isDark(testCoordinates, afterDusk))
    }

    @Test fun `isDark with AlwaysLight returns false`() {
        val skylight = FakeSkylight(testAlwaysLight)

        assertFalse(skylight.isDark(testCoordinates, beforeDawn))
        assertFalse(skylight.isDark(testCoordinates, betweenDawnAndDusk))
        assertFalse(skylight.isDark(testCoordinates, afterDusk))
    }

    @Test fun `isDark with NeverLight returns true`() {
        val skylight = FakeSkylight(testNeverLight)

        assertTrue(skylight.isDark(testCoordinates, beforeDawn))
        assertTrue(skylight.isDark(testCoordinates, betweenDawnAndDusk))
        assertTrue(skylight.isDark(testCoordinates, afterDusk))
    }

    @Test fun `isDark with NeverDaytime returns true before dawn`() {
        val skylight = FakeSkylight(testNeverDaytime)

        assertTrue(skylight.isDark(testCoordinates, beforeDawn))
    }

    @Test fun `isDark with NeverDaytime returns false between dawn and dusk`() {
        val skylight = FakeSkylight(testNeverDaytime)

        assertFalse(skylight.isDark(testCoordinates, betweenDawnAndDusk))
    }

    @Test fun `isDark with NeverDaytime returns true after dusk`() {
        val skylight = FakeSkylight(testNeverDaytime)

        assertTrue(skylight.isDark(testCoordinates, afterDusk))
    }

    @Test fun `isDark with Typical returns true before dawn`() {
        val skylight = FakeSkylight(testTypical)

        assertTrue(skylight.isDark(testCoordinates, beforeDawn))
    }

    @Test fun `isDark with Typical returns false between dawn and dusk`() {
        val skylight = FakeSkylight(testTypical)

        assertFalse(skylight.isDark(testCoordinates, betweenDawnAndDusk))
    }

    @Test fun `isDark with Typical returns true after dusk`() {
        val skylight = FakeSkylight(testTypical)

        assertTrue(skylight.isDark(testCoordinates, afterDusk))
    }
    //endregion
}
