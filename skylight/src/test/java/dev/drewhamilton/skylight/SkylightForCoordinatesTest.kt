package dev.drewhamilton.skylight

import dev.drewhamilton.skylight.test.TestSkylight
import java.time.ZoneOffset
import java.time.ZonedDateTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SkylightForCoordinatesTest {

    private val testCoordinates = Coordinates(98.7, 6.54)

    private val testDateTime1 = ZonedDateTime.of(2019, 5, 20, 15, 0, 0, 0, ZoneOffset.UTC)
    private val testDate1 = testDateTime1.toLocalDate()
    private val testDateTime2 = ZonedDateTime.of(2022, 11, 10, 1, 37, 0, 0, ZoneOffset.UTC)
    private val testDate2 = testDateTime2.toLocalDate()

    private val skylight = TestSkylight(
        testCoordinates to testDate1 to SkylightDay.AlwaysDaytime(testDate1),
        testCoordinates to testDate2 to SkylightDay.NeverLight(testDate2),
    )
    private val skylightForCoordinates = skylight.forCoordinates(testCoordinates)

    @Test fun `getSkylightDay returns SkylightDay from Skylight`() = runTest {
        val result = skylightForCoordinates.getSkylightDay(testDate1)
        assertEquals(SkylightDay.AlwaysDaytime(testDate1), result)
    }

    @Test fun `isLight returns result based on Skylight`() = runTest {
        assertTrue(skylightForCoordinates.isLight(testDateTime1.toInstant()))
        assertFalse(skylightForCoordinates.isLight(testDateTime2.toInstant()))
    }

    @Test fun `isDark returns result based on Skylight`() = runTest {
        assertFalse(skylightForCoordinates.isDark(testDateTime1.toInstant()))
        assertTrue(skylightForCoordinates.isDark(testDateTime2.toInstant()))
    }
}
