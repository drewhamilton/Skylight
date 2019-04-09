package drewhamilton.skylight.rx

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import drewhamilton.skylight.Coordinates
import drewhamilton.skylight.Skylight
import drewhamilton.skylight.SkylightDay
import org.junit.Test
import java.util.Calendar
import java.util.Date

class RxSkylightTest {

    private val timeDifferenceMillis = 5000L

    private val dummyCoordinates = Coordinates(50.0, 60.0)

    private lateinit var mockSkylight: Skylight

    //region getUpcomingSkylightDays
    @Test
    fun `getUpcomingSkylightDays emits today's and tomorrow's info and completes`() {
        val today = today()
        val tomorrow = tomorrow()
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

    private fun today() = Calendar.getInstance()

    private fun tomorrow(): Calendar {
        val tomorrow = Calendar.getInstance()
        tomorrow.add(Calendar.DATE, 1)
        return tomorrow
    }

    private fun dummyTypical(dawn: Date) = SkylightDay.Typical(
        dawn,
        Date(dawn.time + timeDifferenceMillis),
        Date(dawn.time + 2 * timeDifferenceMillis),
        Date(dawn.time + 3 * timeDifferenceMillis)
    )

    private fun SkylightDay.equalsDummyForDate(date: Calendar) =
        this is SkylightDay.Typical &&
                dawn.toCalendar().isSameDay(date) &&
                sunrise.toCalendar().isSameDay(date) &&
                sunset.toCalendar().isSameDay(date) &&
                dusk.toCalendar().isSameDay(date)

    private fun Date.toCalendar(): Calendar {
        val calendar = Calendar.getInstance()
        calendar.time = this
        return calendar
    }

    private fun Calendar.isSameDay(other: Calendar) =
        this.year == other.year &&
                this.month == other.month &&
                this.dayOfMonth == other.dayOfMonth

    private val Calendar.year
        get() = get(Calendar.YEAR)

    private val Calendar.month
        get() = get(Calendar.MONTH)

    private val Calendar.dayOfMonth
        get() = get(Calendar.DAY_OF_MONTH)

}
