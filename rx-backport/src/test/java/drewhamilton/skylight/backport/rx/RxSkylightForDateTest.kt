package drewhamilton.skylight.backport.rx

import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import drewhamilton.skylight.backport.Coordinates
import drewhamilton.skylight.backport.SkylightDay
import drewhamilton.skylight.backport.SkylightForDate
import org.junit.Test
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetTime
import org.threeten.bp.ZoneOffset

class RxSkylightForDateTest {

    private val dummyCoordinates = Coordinates(70.0, 80.0)
    private val testDawn = OffsetTime.of(8, 0, 0, 0, ZoneOffset.UTC)

    private lateinit var mockSkylightForDate: SkylightForDate

    //region getSkylightDaySingle
    @Test
    fun `getSkylightDaySingle emits info and completes`() {
        mockSkylight { dummyTypical(LocalDate.now(), testDawn) }

        mockSkylightForDate.getSkylightDaySingle(dummyCoordinates).test()
            .assertComplete()
            .assertValueCount(1)
            .assertValueAt(0) { it == dummyTypical(LocalDate.now(), testDawn) }
    }
    //endregion

    private fun mockSkylight(returnFunction: () -> SkylightDay) {
        mockSkylightForDate = mock {
            on { getSkylightDay(dummyCoordinates) } doAnswer { returnFunction() }
        }
    }

}
