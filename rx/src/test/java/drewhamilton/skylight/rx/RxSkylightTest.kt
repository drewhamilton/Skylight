package drewhamilton.skylight.rx

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import drewhamilton.skylight.Coordinates
import drewhamilton.skylight.NewSkylightDay
import drewhamilton.skylight.Skylight
import org.junit.Test
import java.time.LocalDate
import java.time.OffsetTime
import java.time.ZoneOffset

class RxSkylightTest {

    private val dummyCoordinates = Coordinates(50.0, 60.0)
    private val testDawn = OffsetTime.of(12, 0, 0, 0, ZoneOffset.UTC)

    private lateinit var mockSkylight: Skylight

    //region getSkylightDaySingle
    @Test
    fun `getSkylightDaySingle emits info and completes`() {
        mockSkylight { date, dawn -> dummyTypical(date, dawn) }

        mockSkylight.getSkylightDaySingle(dummyCoordinates, LocalDate.now()).test()
            .assertComplete()
            .assertValueCount(1)
            .assertValueAt(0) { it == dummyTypical(LocalDate.now(), testDawn) }
    }
    //endregion

    //region getUpcomingSkylightDays
    @Test
    fun `getUpcomingSkylightDays emits today's and tomorrow's info and completes`() {
        mockSkylight { date, dawn -> dummyTypical(date, dawn) }

        mockSkylight.getUpcomingSkylightDays(dummyCoordinates).test()
            .assertComplete()
            .assertValueCount(2)
            .assertValueAt(0) { it == dummyTypical(LocalDate.now(), testDawn) }
            .assertValueAt(1) { it == dummyTypical(LocalDate.now().plusDays(1), testDawn) }
    }
    //endregion

    private fun mockSkylight(returnFunction: (LocalDate, OffsetTime) -> NewSkylightDay) {
        mockSkylight = mock {
            on { getSkylightDay(any(), any<LocalDate>()) } doAnswer { invocation ->
                returnFunction(invocation.getArgument(1), testDawn)
            }
        }
    }
}
