package drewhamilton.skylight.rx

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import drewhamilton.skylight.Coordinates
import drewhamilton.skylight.Skylight
import drewhamilton.skylight.SkylightDay
import org.junit.Test
import java.util.Date

class RxSkylightTest {

    private val dummyCoordinates = Coordinates(50.0, 60.0)

    private lateinit var mockSkylight: Skylight

    //region getSkylightDaySingle
    @Test
    fun `getSkylightDaySingle emits info and completes`() {
        mockSkylight { dummyTypical(it) }

        mockSkylight.getSkylightDaySingle(dummyCoordinates, today()).test()
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

        mockSkylight.getUpcomingSkylightDays(dummyCoordinates).test()
            .assertComplete()
            .assertValueCount(2)
            .assertValueAt(0) { it.equalsDummyForDate(today) }
            .assertValueAt(1) { it.equalsDummyForDate(tomorrow) }
    }
    //endregion

    private fun mockSkylight(returnFunction: (Date) -> SkylightDay) {
        mockSkylight = mock {
            on { getSkylightDay(any(), any()) } doAnswer { invocation ->
                returnFunction(invocation.getArgument(1))
            }
        }
    }
}
