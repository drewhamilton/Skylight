package drewhamilton.skylight.rx

import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import drewhamilton.skylight.Coordinates
import drewhamilton.skylight.NewSkylightDay
import drewhamilton.skylight.SkylightForDate
import org.junit.Test
import java.time.LocalDate
import java.time.OffsetTime
import java.time.ZoneOffset

class RxSkylightForDateTest {

    private val dummyCoordinates = Coordinates(70.0, 80.0)
    private val testDawn = OffsetTime.of(8, 0, 0, 0, ZoneOffset.UTC)

    private lateinit var mockSkylightForDate: SkylightForDate

    //region getSkylightDaySingle
    @Test
    fun `getSkylightDaySingle emits info and completes`() {
        mockSkylight { dummyTypical(LocalDate.now(), testDawn) }

        mockSkylightForDate.getNewSkylightDaySingle(dummyCoordinates).test()
            .assertComplete()
            .assertValueCount(1)
            .assertValueAt(0) { it == dummyTypical(LocalDate.now(), testDawn) }
    }
    //endregion

    private fun mockSkylight(returnFunction: () -> NewSkylightDay) {
        mockSkylightForDate = mock {
            on { getNewSkylightDay(dummyCoordinates) } doAnswer { returnFunction() }
        }
    }

}
