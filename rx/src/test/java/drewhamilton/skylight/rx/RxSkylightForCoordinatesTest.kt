package drewhamilton.skylight.rx

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import drewhamilton.skylight.SkylightDay
import drewhamilton.skylight.SkylightForCoordinates
import org.junit.Test
import java.util.Date

class RxSkylightForCoordinatesTest {

    private lateinit var mockSkylightForCoordinates: SkylightForCoordinates

    //region getSkylightDaySingle
    @Test
    fun `getSkylightDaySingle emits info and completes`() {
        mockSkylight { dummyTypical(it) }

        mockSkylightForCoordinates.getSkylightDaySingle(today()).test()
            .assertComplete()
            .assertValueCount(1)
            .assertValueAt(0) { it.equalsDummyForDate(todayCalendar()) }
    }
    //endregion

    //region getUpcomingSkylightDays
    @Test
    fun `getUpcomingSkylightDays emits today's and tomorrow's info and completes`() {
        val today = todayCalendar()
        val tomorrow = tomorrowCalendar()
        mockSkylight { dummyTypical(it) }

        mockSkylightForCoordinates.getUpcomingSkylightDays().test()
            .assertComplete()
            .assertValueCount(2)
            .assertValueAt(0) { it.equalsDummyForDate(today) }
            .assertValueAt(1) { it.equalsDummyForDate(tomorrow) }
    }
    //endregion

    private fun mockSkylight(returnFunction: (Date) -> SkylightDay) {
        mockSkylightForCoordinates = mock {
            on { getSkylightDay(any()) } doAnswer { invocation ->
                returnFunction(invocation.getArgument(0))
            }
        }
    }

}
