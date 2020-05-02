package dev.drewhamilton.skylight

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.Month

class SkylightForDateTest {

    private lateinit var mockSkylight: Skylight
    private val testDate = LocalDate.of(2019, Month.MAY, 20)

    private lateinit var skylightForDate: SkylightForDate

    @Before
    fun setUp() {
        mockSkylight = mock()
        skylightForDate = mockSkylight.forDate(testDate)
    }

    @Test
    fun `getSkylightDay passes coordinates and date to Skylight`() {
        val testCoordinates = Coordinates(98.76, 54.321)
        skylightForDate.getSkylightDay(testCoordinates)

        verify(mockSkylight).getSkylightDay(testCoordinates, testDate)
        verifyNoMoreInteractions(mockSkylight)
    }
}
