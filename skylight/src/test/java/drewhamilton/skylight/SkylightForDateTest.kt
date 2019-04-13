package drewhamilton.skylight

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Before
import org.junit.Test
import java.util.Date

class SkylightForDateTest {

    private lateinit var mockSkylight: Skylight
    private val testDate = Date(12387)

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
