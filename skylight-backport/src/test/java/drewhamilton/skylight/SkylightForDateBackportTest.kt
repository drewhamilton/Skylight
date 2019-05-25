package drewhamilton.skylight

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import drewhamilton.skylight.backport.Coordinates
import drewhamilton.skylight.backport.SkylightBackport
import drewhamilton.skylight.backport.SkylightForDateBackport
import drewhamilton.skylight.backport.forDate
import org.junit.Before
import org.junit.Test
import org.threeten.bp.LocalDate
import org.threeten.bp.Month

class SkylightForDateBackportTest {

    private lateinit var mockSkylight: SkylightBackport
    private val testDate = LocalDate.of(2019, Month.MAY, 20)

    private lateinit var skylightForDate: SkylightForDateBackport

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
