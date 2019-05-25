package drewhamilton.skylight.backport.rx

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import drewhamilton.skylight.backport.Coordinates
import drewhamilton.skylight.backport.SkylightBackport
import drewhamilton.skylight.backport.SkylightDay
import org.junit.Test
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetTime
import org.threeten.bp.ZoneOffset

class RxSkylightTest {

    private val dummyCoordinates = Coordinates(50.0, 60.0)
    private val testDawn = OffsetTime.of(12, 0, 0, 0, ZoneOffset.UTC)

    private lateinit var mockSkylight: SkylightBackport

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

    private fun mockSkylight(returnFunction: (LocalDate, OffsetTime) -> SkylightDay) {
        mockSkylight = mock {
            on { getSkylightDay(any(), any()) } doAnswer { invocation ->
                returnFunction(invocation.getArgument(1), testDawn)
            }
        }
    }
}
