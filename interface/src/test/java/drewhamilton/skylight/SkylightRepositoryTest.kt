package drewhamilton.skylight

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import drewhamilton.skylight.models.*
import io.reactivex.Single
import org.junit.Test
import java.util.*

class SkylightRepositoryTest {

    private val timeDifferenceMillis = 5000L

    private val dummyCoordinates = Coordinates(50.0, 60.0)
    private val dummyDate = Date(9876543210L)

    private lateinit var mockSkylightRepository: SkylightRepository

    //region getUpcomingSkylightInfo
    @Test
    fun `getUpcomingSkylightInfo emits today's and tomorrow's info and completes`() {
        val today = today()
        val tomorrow = tomorrow()
        mockSkylightRepository { dummyTypical(it) }

        mockSkylightRepository.getUpcomingSkylightInfo(dummyCoordinates).test()
            .assertComplete()
            .assertValueCount(2)
            .assertValueAt(0) { it.equalsDummyForDate(today) }
            .assertValueAt(1) { it.equalsDummyForDate(tomorrow) }
    }
    //endregion

    //region isLight
    @Test
    fun `isLight with AlwaysDaytime returns true`() {
        mockSkylightRepository { dummyAlwaysDaytime() }

        mockSkylightRepository.isLight(dummyCoordinates, dummyDate).test()
            .assertComplete()
            .assertValueCount(1)
            .assertValue(true)
    }

    @Test
    fun `isLight with AlwaysLight returns true`() {
        mockSkylightRepository { dummyAlwaysLight(it) }

        mockSkylightRepository.isLight(dummyCoordinates, dummyDate).test()
            .assertComplete()
            .assertValueCount(1)
            .assertValue(true)
    }

    @Test
    fun `isLight with NeverLight returns false`() {
        mockSkylightRepository { dummyNeverLight() }

        mockSkylightRepository.isLight(dummyCoordinates, dummyDate).test()
            .assertComplete()
            .assertValueCount(1)
            .assertValue(false)
    }

    @Test
    fun `isLight with NeverDaytime returns false before dawn`() {
        mockSkylightRepository { dummyNeverDaytime(Date(it.time + timeDifferenceMillis)) }

        mockSkylightRepository.isLight(dummyCoordinates, dummyDate).test()
            .assertComplete()
            .assertValueCount(1)
            .assertValue(false)
    }

    @Test
    fun `isLight with NeverDaytime returns true between dawn and dusk`() {
        mockSkylightRepository { dummyNeverDaytime(Date(it.time - timeDifferenceMillis/2)) }

        mockSkylightRepository.isLight(dummyCoordinates, dummyDate).test()
            .assertComplete()
            .assertValueCount(1)
            .assertValue(true)
    }

    @Test
    fun `isLight with NeverDaytime returns false after dusk`() {
        mockSkylightRepository { dummyNeverDaytime(Date(it.time - 2*timeDifferenceMillis)) }

        mockSkylightRepository.isLight(dummyCoordinates, dummyDate).test()
            .assertComplete()
            .assertValueCount(1)
            .assertValue(false)
    }

    @Test
    fun `isLight with Typical returns false before dawn`() {
        mockSkylightRepository { dummyTypical(Date(it.time + timeDifferenceMillis)) }

        mockSkylightRepository.isLight(dummyCoordinates, dummyDate).test()
            .assertComplete()
            .assertValueCount(1)
            .assertValue(false)
    }

    @Test
    fun `isLight with Typical returns true between dawn and dusk`() {
        mockSkylightRepository { dummyTypical(Date(it.time - timeDifferenceMillis/2)) }

        mockSkylightRepository.isLight(dummyCoordinates, dummyDate).test()
            .assertComplete()
            .assertValueCount(1)
            .assertValue(true)
    }

    @Test
    fun `isLight with Typical returns false after dusk`() {
        mockSkylightRepository { dummyTypical(Date(it.time - 4*timeDifferenceMillis)) }

        mockSkylightRepository.isLight(dummyCoordinates, dummyDate).test()
            .assertComplete()
            .assertValueCount(1)
            .assertValue(false)
    }
    //endregion

    //region isDark
    @Test
    fun `isDark with AlwaysDaytime returns false`() {
        mockSkylightRepository { dummyAlwaysDaytime() }

        mockSkylightRepository.isDark(dummyCoordinates, dummyDate).test()
            .assertComplete()
            .assertValueCount(1)
            .assertValue(false)
    }

    @Test
    fun `isDark with AlwaysLight returns false`() {
        mockSkylightRepository { dummyAlwaysLight(it) }

        mockSkylightRepository.isDark(dummyCoordinates, dummyDate).test()
            .assertComplete()
            .assertValueCount(1)
            .assertValue(false)
    }

    @Test
    fun `isDark with NeverLight returns true`() {
        mockSkylightRepository { dummyNeverLight() }

        mockSkylightRepository.isDark(dummyCoordinates, dummyDate).test()
            .assertComplete()
            .assertValueCount(1)
            .assertValue(true)
    }

    @Test
    fun `isDark with NeverDaytime returns true before dawn`() {
        mockSkylightRepository { dummyNeverDaytime(Date(it.time + timeDifferenceMillis)) }

        mockSkylightRepository.isDark(dummyCoordinates, dummyDate).test()
            .assertComplete()
            .assertValueCount(1)
            .assertValue(true)
    }

    @Test
    fun `isDark with NeverDaytime returns false between dawn and dusk`() {
        mockSkylightRepository { dummyNeverDaytime(Date(it.time - timeDifferenceMillis/2)) }

        mockSkylightRepository.isDark(dummyCoordinates, dummyDate).test()
            .assertComplete()
            .assertValueCount(1)
            .assertValue(false)
    }

    @Test
    fun `isDark with NeverDaytime returns true after dusk`() {
        mockSkylightRepository { dummyNeverDaytime(Date(it.time - 2*timeDifferenceMillis)) }

        mockSkylightRepository.isDark(dummyCoordinates, dummyDate).test()
            .assertComplete()
            .assertValueCount(1)
            .assertValue(true)
    }

    @Test
    fun `isDark with Typical returns true before dawn`() {
        mockSkylightRepository { dummyTypical(Date(it.time + timeDifferenceMillis)) }

        mockSkylightRepository.isDark(dummyCoordinates, dummyDate).test()
            .assertComplete()
            .assertValueCount(1)
            .assertValue(true)
    }

    @Test
    fun `isDark with Typical returns false between dawn and dusk`() {
        mockSkylightRepository { dummyTypical(Date(it.time - timeDifferenceMillis/2)) }

        mockSkylightRepository.isDark(dummyCoordinates, dummyDate).test()
            .assertComplete()
            .assertValueCount(1)
            .assertValue(false)
    }

    @Test
    fun `isDark with Typical returns true after dusk`() {
        mockSkylightRepository { dummyTypical(Date(it.time - 4*timeDifferenceMillis)) }

        mockSkylightRepository.isDark(dummyCoordinates, dummyDate).test()
            .assertComplete()
            .assertValueCount(1)
            .assertValue(true)
    }
    //endregion

    private fun mockSkylightRepository(returnFunction: (Date) -> SkylightInfo) {
        mockSkylightRepository = mock { _ ->
            on { getSkylightInfo(any(), any()) } doAnswer {
                Single.fromCallable { returnFunction(it.getArgument(1)) }
            }
        }
    }

    private fun today() = Calendar.getInstance()

    private fun tomorrow(): Calendar {
        val tomorrow = Calendar.getInstance()
        tomorrow.add(Calendar.DATE, 1)
        return tomorrow
    }

    private fun dummyAlwaysDaytime() = AlwaysDaytime()
    private fun dummyAlwaysLight(sunrise: Date) = AlwaysLight(sunrise, Date(sunrise.time + timeDifferenceMillis))
    private fun dummyNeverLight() = NeverLight()
    private fun dummyNeverDaytime(dawn: Date) = NeverDaytime(dawn, Date(dawn.time + timeDifferenceMillis))

    private fun dummyTypical(dawn: Date) = Typical(
        dawn,
        Date(dawn.time + timeDifferenceMillis),
        Date(dawn.time + 2*timeDifferenceMillis),
        Date(dawn.time + 3*timeDifferenceMillis)
    )

    private fun SkylightInfo.equalsDummyForDate(date: Calendar) =
        this is Typical &&
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
