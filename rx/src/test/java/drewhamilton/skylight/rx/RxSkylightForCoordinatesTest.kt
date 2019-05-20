package drewhamilton.skylight.rx

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import drewhamilton.skylight.NewSkylightDay
import drewhamilton.skylight.SkylightForCoordinates
import org.junit.Test
import java.time.LocalDate
import java.time.OffsetTime
import java.time.ZoneOffset

class RxSkylightForCoordinatesTest {

    private val testDawn = OffsetTime.of(12, 0, 0, 0, ZoneOffset.UTC)

    private lateinit var mockSkylightForCoordinates: SkylightForCoordinates

    //region getSkylightDaySingle
    @Test
    fun `getSkylightDaySingle emits info and completes`() {
        mockSkylight { date, dawn -> dummyTypical(date, dawn) }

        mockSkylightForCoordinates.getSkylightDaySingle(LocalDate.now()).test()
            .assertComplete()
            .assertValueCount(1)
            .assertValueAt(0) { it == dummyTypical(LocalDate.now(), testDawn) }
    }
    //endregion

    //region getUpcomingSkylightDays
    @Test
    fun `getUpcomingSkylightDays emits today's and tomorrow's info and completes`() {
        mockSkylight { date, dawn -> dummyTypical(date, dawn) }

        mockSkylightForCoordinates.getUpcomingSkylightDays().test()
            .assertComplete()
            .assertValueCount(2)
            .assertValueAt(0) { it == dummyTypical(LocalDate.now(), testDawn) }
            .assertValueAt(1) { it == dummyTypical(LocalDate.now().plusDays(1), testDawn) }
    }
    //endregion

    private fun mockSkylight(returnFunction: (LocalDate, OffsetTime) -> NewSkylightDay) {
        mockSkylightForCoordinates = mock {
            on { getSkylightDay(any<LocalDate>()) } doAnswer { invocation ->
                val date: LocalDate = invocation.getArgument(0)
                returnFunction(date, testDawn)
            }
        }
    }
}
