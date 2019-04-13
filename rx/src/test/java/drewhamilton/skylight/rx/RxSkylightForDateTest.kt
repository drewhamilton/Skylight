package drewhamilton.skylight.rx

import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import drewhamilton.skylight.Coordinates
import drewhamilton.skylight.SkylightDay
import drewhamilton.skylight.SkylightForDate
import org.junit.Test

class RxSkylightForDateTest {

    private val dummyCoordinates = Coordinates(70.0, 80.0)

    private lateinit var mockSkylightForDate: SkylightForDate

    //region getSkylightDaySingle
    @Test
    fun `getSkylightDaySingle emits info and completes`() {
        mockSkylight { dummyTypical(today()) }

        mockSkylightForDate.getSkylightDaySingle(dummyCoordinates).test()
            .assertComplete()
            .assertValueCount(1)
            .assertValueAt(0) { it.equalsDummyForDate(todayCalendar()) }
    }
    //endregion

    private fun mockSkylight(returnFunction: () -> SkylightDay) {
        mockSkylightForDate = mock {
            on { getSkylightDay(dummyCoordinates) } doAnswer { returnFunction() }
        }
    }

}
