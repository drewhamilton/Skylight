package dev.drewhamilton.skylight

import dev.drewhamilton.skylight.test.TestSkylight
import java.time.LocalDate
import java.time.Month
import java.time.ZoneOffset
import java.time.ZonedDateTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SkylightTest {

    private val testCoordinates = Coordinates(50.0, 60.0)
    private val typicalDate = LocalDate.of(2019, Month.MAY, 15)
    private val alwaysDaytimeDate = LocalDate.of(2019, Month.JUNE, 15)
    private val alwaysLightDate = LocalDate.of(2019, Month.JULY, 15)
    private val neverLightDate = LocalDate.of(2019, Month.AUGUST, 15)
    private val neverDaytimeDate = LocalDate.of(2019, Month.SEPTEMBER, 15)

    private val testDawn = ZonedDateTime.of(2019, 5, 15, 8, 0, 0, 0, ZoneOffset.UTC)
    private val testSunrise = ZonedDateTime.of(2019, 5, 15, 10, 0, 0, 0, ZoneOffset.UTC)
    private val testSunset = ZonedDateTime.of(2019, 5, 15, 14, 0, 0, 0, ZoneOffset.UTC)
    private val testDusk = ZonedDateTime.of(2019, 5, 15, 16, 0, 0, 0, ZoneOffset.UTC)

    private val beforeDawn = ZonedDateTime.of(typicalDate, testDawn.minusHours(2).toLocalTime(), ZoneOffset.UTC)
    private val betweenSunriseAndSunset =
        ZonedDateTime.of(typicalDate, testSunrise.plusHours(2).toLocalTime(), ZoneOffset.UTC)
    private val afterDusk = ZonedDateTime.of(typicalDate, testDusk.plusHours(2).toLocalTime(), ZoneOffset.UTC)
    private val beforeSunrise = ZonedDateTime.of(typicalDate, testSunrise.minusHours(1).toLocalTime(), ZoneOffset.UTC)
    private val afterSunset = ZonedDateTime.of(typicalDate, testSunset.plusHours(1).toLocalTime(), ZoneOffset.UTC)

    private val testAlwaysDaytime = SkylightDay.AlwaysDaytime(date = alwaysDaytimeDate)
    private val testAlwaysLight = SkylightDay.Typical(
        date = alwaysLightDate,
        sunrise = testSunrise.withDate(alwaysLightDate).toInstant(),
        sunset = testSunset.withDate(alwaysLightDate).toInstant()
    )
    private val testNeverLight = SkylightDay.NeverLight(date = neverLightDate)
    private val testNeverDaytime = SkylightDay.Typical(
        date = neverDaytimeDate,
        dawn = testDawn.withDate(neverDaytimeDate).toInstant(),
        dusk = testDusk.withDate(neverDaytimeDate).toInstant()
    )
    private val testTypical = SkylightDay.Typical (
        date = typicalDate,
        dawn = testDawn.toInstant(),
        sunrise = testSunrise.toInstant(),
        sunset = testSunset.toInstant(),
        dusk = testDusk.toInstant()
    )

    private val skylight = TestSkylight(
        testCoordinates to alwaysDaytimeDate to testAlwaysDaytime,
        testCoordinates to alwaysLightDate to testAlwaysLight,
        testCoordinates to neverLightDate to testNeverLight,
        testCoordinates to neverDaytimeDate to testNeverDaytime,
        testCoordinates to typicalDate to testTypical,
    )

    //region isLight
    @Test fun `isLight with AlwaysDaytime returns true`() = runTest {
        assertTrue(skylight.isLight(testCoordinates, beforeDawn.withDate(alwaysDaytimeDate).toInstant()))
        assertTrue(skylight.isLight(testCoordinates, betweenSunriseAndSunset.withDate(alwaysDaytimeDate).toInstant()))
        assertTrue(skylight.isLight(testCoordinates, afterDusk.withDate(alwaysDaytimeDate).toInstant()))
    }

    @Test fun `isLight with AlwaysLight returns true`() = runTest {
        assertTrue(skylight.isLight(testCoordinates, beforeDawn.withDate(alwaysLightDate).toInstant()))
        assertTrue(skylight.isLight(testCoordinates, betweenSunriseAndSunset.withDate(alwaysLightDate).toInstant()))
        assertTrue(skylight.isLight(testCoordinates, afterDusk.withDate(alwaysLightDate).toInstant()))
    }

    @Test fun `isLight with NeverLight returns false`() = runTest {
        assertFalse(skylight.isLight(testCoordinates, beforeDawn.withDate(neverLightDate).toInstant()))
        assertFalse(skylight.isLight(testCoordinates, betweenSunriseAndSunset.withDate(neverLightDate).toInstant()))
        assertFalse(skylight.isLight(testCoordinates, afterDusk.withDate(neverLightDate).toInstant()))
    }

    @Test fun `isLight with NeverDaytime returns false before dawn`() = runTest {
        assertFalse(skylight.isLight(testCoordinates, beforeDawn.withDate(neverDaytimeDate).toInstant()))
    }

    @Test fun `isLight with NeverDaytime returns true between dawn and dusk`() = runTest {
        assertTrue(skylight.isLight(testCoordinates, betweenSunriseAndSunset.withDate(neverDaytimeDate).toInstant()))
    }

    @Test fun `isLight with NeverDaytime returns false after dusk`() = runTest {
        assertFalse(skylight.isLight(testCoordinates, afterDusk.withDate(neverDaytimeDate).toInstant()))
    }

    @Test fun `isLight with Typical returns false before dawn`() = runTest {
        assertFalse(skylight.isLight(testCoordinates, beforeDawn.withDate(typicalDate).toInstant()))
    }

    @Test fun `isLight with Typical returns true between dawn and dusk`() = runTest {
        assertTrue(skylight.isLight(testCoordinates, betweenSunriseAndSunset.withDate(typicalDate).toInstant()))
    }

    @Test fun `isLight with Typical returns false after dusk`() = runTest {
        assertFalse(skylight.isLight(testCoordinates, afterDusk.withDate(typicalDate).toInstant()))
    }
    //endregion

    //region isDark
    @Test fun `isDark with AlwaysDaytime returns false`() = runTest {
        assertFalse(skylight.isDark(testCoordinates, beforeDawn.withDate(alwaysDaytimeDate).toInstant()))
        assertFalse(skylight.isDark(testCoordinates, betweenSunriseAndSunset.withDate(alwaysDaytimeDate).toInstant()))
        assertFalse(skylight.isDark(testCoordinates, afterDusk.withDate(alwaysDaytimeDate).toInstant()))
    }

    @Test fun `isDark with AlwaysLight returns false`() = runTest {
        assertFalse(skylight.isDark(testCoordinates, beforeDawn.withDate(alwaysLightDate).toInstant()))
        assertFalse(skylight.isDark(testCoordinates, betweenSunriseAndSunset.withDate(alwaysLightDate).toInstant()))
        assertFalse(skylight.isDark(testCoordinates, afterDusk.withDate(alwaysLightDate).toInstant()))
    }

    @Test fun `isDark with NeverLight returns true`() = runTest {
        assertTrue(skylight.isDark(testCoordinates, beforeDawn.withDate(neverLightDate).toInstant()))
        assertTrue(skylight.isDark(testCoordinates, betweenSunriseAndSunset.withDate(neverLightDate).toInstant()))
        assertTrue(skylight.isDark(testCoordinates, afterDusk.withDate(neverLightDate).toInstant()))
    }

    @Test fun `isDark with NeverDaytime returns true before dawn`() = runTest {
        assertTrue(skylight.isDark(testCoordinates, beforeDawn.withDate(neverDaytimeDate).toInstant()))
    }

    @Test fun `isDark with NeverDaytime returns false between dawn and dusk`() = runTest {
        assertFalse(skylight.isDark(testCoordinates, betweenSunriseAndSunset.withDate(neverDaytimeDate).toInstant()))
    }

    @Test fun `isDark with NeverDaytime returns true after dusk`() = runTest {
        assertTrue(skylight.isDark(testCoordinates, afterDusk.withDate(neverDaytimeDate).toInstant()))
    }

    @Test fun `isDark with Typical returns true before dawn`() = runTest {
        assertTrue(skylight.isDark(testCoordinates, beforeDawn.withDate(typicalDate).toInstant()))
    }

    @Test fun `isDark with Typical returns false between dawn and dusk`() = runTest {
        assertFalse(skylight.isDark(testCoordinates, betweenSunriseAndSunset.withDate(typicalDate).toInstant()))
    }

    @Test fun `isDark with Typical returns true after dusk`() = runTest {
        assertTrue(skylight.isDark(testCoordinates, afterDusk.withDate(typicalDate).toInstant()))
    }
    //endregion

    //region isDaytime
    @Test fun `isDaytime with AlwaysDaytime returns true`() = runTest {
        assertTrue(skylight.isDaytime(testCoordinates, beforeDawn.withDate(alwaysDaytimeDate).toInstant()))
        assertTrue(skylight.isDaytime(testCoordinates, betweenSunriseAndSunset.withDate(alwaysDaytimeDate).toInstant()))
        assertTrue(skylight.isDaytime(testCoordinates, afterDusk.withDate(alwaysDaytimeDate).toInstant()))
    }

    @Test fun `isDaytime with AlwaysLight returns false before sunrise`() = runTest {
        assertFalse(skylight.isDaytime(testCoordinates, beforeSunrise.withDate(alwaysLightDate).toInstant()))
    }

    @Test fun `isDaytime with AlwaysLight returns true between sunrise and sunset`() = runTest {
        assertTrue(skylight.isDaytime(testCoordinates, betweenSunriseAndSunset.withDate(alwaysLightDate).toInstant()))
    }

    @Test fun `isDaytime with AlwaysLight returns false after sunset`() = runTest {
        assertFalse(skylight.isDaytime(testCoordinates, afterDusk.withDate(alwaysLightDate).toInstant()))
    }

    @Test fun `isDaytime with NeverLight returns false`() = runTest {
        assertFalse(skylight.isDaytime(testCoordinates, beforeDawn.withDate(neverLightDate).toInstant()))
        assertFalse(skylight.isDaytime(testCoordinates, betweenSunriseAndSunset.withDate(neverLightDate).toInstant()))
        assertFalse(skylight.isDaytime(testCoordinates, afterDusk.withDate(neverLightDate).toInstant()))
    }

    @Test fun `isDaytime with NeverDaytime returns false`() = runTest {
        assertFalse(skylight.isDaytime(testCoordinates, beforeSunrise.withDate(neverDaytimeDate).toInstant()))
        assertFalse(skylight.isDaytime(testCoordinates, betweenSunriseAndSunset.withDate(neverDaytimeDate).toInstant()))
        assertFalse(skylight.isDaytime(testCoordinates, afterSunset.withDate(neverDaytimeDate).toInstant()))
    }

    @Test fun `isDaytime with Typical returns false before sunrise`() = runTest {
        assertFalse(skylight.isDaytime(testCoordinates, beforeSunrise.withDate(typicalDate).toInstant()))
    }

    @Test fun `isDaytime with Typical returns true between sunrise and sunset`() = runTest {
        assertTrue(skylight.isDaytime(testCoordinates, betweenSunriseAndSunset.withDate(typicalDate).toInstant()))
    }

    @Test fun `isDaytime with Typical returns false after sunset`() = runTest {
        assertFalse(skylight.isDaytime(testCoordinates, afterSunset.withDate(typicalDate).toInstant()))
    }
    //endregion

    private fun ZonedDateTime.withDate(date: LocalDate): ZonedDateTime =
        withYear(date.year).withMonth(date.monthValue).withDayOfMonth(date.dayOfMonth)
}
