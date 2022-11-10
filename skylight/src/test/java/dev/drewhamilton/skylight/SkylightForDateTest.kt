package dev.drewhamilton.skylight

import dev.drewhamilton.skylight.test.TestSkylight
import java.time.LocalDate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SkylightForDateTest {

    private val testCoordinates = Coordinates(98.7, 6.54)

    private val testDate = LocalDate.of(2019, 5, 20)

    private val skylight = TestSkylight(
        testCoordinates to testDate to SkylightDay.AlwaysDaytime(testDate),
    )
    private val skylightForDate = skylight.forDate(testDate)

    @Test fun `getSkylightDay returns SkylightDay from Skylight`() = runTest {
        val result = skylightForDate.getSkylightDay(testCoordinates)
        assertEquals(SkylightDay.AlwaysDaytime(testDate), result)
    }
}
