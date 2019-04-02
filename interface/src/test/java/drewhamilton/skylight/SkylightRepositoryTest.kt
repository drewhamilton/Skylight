package drewhamilton.skylight

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import drewhamilton.skylight.models.AlwaysDaytime
import drewhamilton.skylight.models.AlwaysLight
import drewhamilton.skylight.models.Coordinates
import drewhamilton.skylight.models.NeverDaytime
import drewhamilton.skylight.models.NeverLight
import drewhamilton.skylight.models.SkylightInfo
import drewhamilton.skylight.models.Typical
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.Date

class SkylightRepositoryTest {

    private val timeDifferenceMillis = 5000L

    private val dummyCoordinates = Coordinates(50.0, 60.0)
    private val dummyDate = Date(9876543210L)

    private lateinit var mockSkylightRepository: SkylightRepository

    //region isLight
    @Test
    fun `isLight with AlwaysDaytime returns true`() {
        mockSkylightRepository { dummyAlwaysDaytime() }

        assertTrue(mockSkylightRepository.isLight(dummyCoordinates, dummyDate))
    }

    @Test
    fun `isLight with AlwaysLight returns true`() {
        mockSkylightRepository { dummyAlwaysLight(it) }

        assertTrue(mockSkylightRepository.isLight(dummyCoordinates, dummyDate))
    }

    @Test
    fun `isLight with NeverLight returns false`() {
        mockSkylightRepository { dummyNeverLight() }

        assertFalse(mockSkylightRepository.isLight(dummyCoordinates, dummyDate))
    }

    @Test
    fun `isLight with NeverDaytime returns false before dawn`() {
        mockSkylightRepository { dummyNeverDaytime(Date(it.time + timeDifferenceMillis)) }

        assertFalse(mockSkylightRepository.isLight(dummyCoordinates, dummyDate))
    }

    @Test
    fun `isLight with NeverDaytime returns true between dawn and dusk`() {
        mockSkylightRepository { dummyNeverDaytime(Date(it.time - timeDifferenceMillis/2)) }

        assertTrue(mockSkylightRepository.isLight(dummyCoordinates, dummyDate))
    }

    @Test
    fun `isLight with NeverDaytime returns false after dusk`() {
        mockSkylightRepository { dummyNeverDaytime(Date(it.time - 2*timeDifferenceMillis)) }

        assertFalse(mockSkylightRepository.isLight(dummyCoordinates, dummyDate))
    }

    @Test
    fun `isLight with Typical returns false before dawn`() {
        mockSkylightRepository { dummyTypical(Date(it.time + timeDifferenceMillis)) }

        assertFalse(mockSkylightRepository.isLight(dummyCoordinates, dummyDate))
    }

    @Test
    fun `isLight with Typical returns true between dawn and dusk`() {
        mockSkylightRepository { dummyTypical(Date(it.time - timeDifferenceMillis/2)) }

        assertTrue(mockSkylightRepository.isLight(dummyCoordinates, dummyDate))
    }

    @Test
    fun `isLight with Typical returns false after dusk`() {
        mockSkylightRepository { dummyTypical(Date(it.time - 4*timeDifferenceMillis)) }

        assertFalse(mockSkylightRepository.isLight(dummyCoordinates, dummyDate))
    }
    //endregion

    //region isDark
    @Test
    fun `isDark with AlwaysDaytime returns false`() {
        mockSkylightRepository { dummyAlwaysDaytime() }

        assertFalse(mockSkylightRepository.isDark(dummyCoordinates, dummyDate))
    }

    @Test
    fun `isDark with AlwaysLight returns false`() {
        mockSkylightRepository { dummyAlwaysLight(it) }

        assertFalse(mockSkylightRepository.isDark(dummyCoordinates, dummyDate))
    }

    @Test
    fun `isDark with NeverLight returns true`() {
        mockSkylightRepository { dummyNeverLight() }

        assertTrue(mockSkylightRepository.isDark(dummyCoordinates, dummyDate))
    }

    @Test
    fun `isDark with NeverDaytime returns true before dawn`() {
        mockSkylightRepository { dummyNeverDaytime(Date(it.time + timeDifferenceMillis)) }

        assertTrue(mockSkylightRepository.isDark(dummyCoordinates, dummyDate))
    }

    @Test
    fun `isDark with NeverDaytime returns false between dawn and dusk`() {
        mockSkylightRepository { dummyNeverDaytime(Date(it.time - timeDifferenceMillis/2)) }

        assertFalse(mockSkylightRepository.isDark(dummyCoordinates, dummyDate))
    }

    @Test
    fun `isDark with NeverDaytime returns true after dusk`() {
        mockSkylightRepository { dummyNeverDaytime(Date(it.time - 2*timeDifferenceMillis)) }

        assertTrue(mockSkylightRepository.isDark(dummyCoordinates, dummyDate))
    }

    @Test
    fun `isDark with Typical returns true before dawn`() {
        mockSkylightRepository { dummyTypical(Date(it.time + timeDifferenceMillis)) }

        assertTrue(mockSkylightRepository.isDark(dummyCoordinates, dummyDate))
    }

    @Test
    fun `isDark with Typical returns false between dawn and dusk`() {
        mockSkylightRepository { dummyTypical(Date(it.time - timeDifferenceMillis/2)) }

        assertFalse(mockSkylightRepository.isDark(dummyCoordinates, dummyDate))
    }

    @Test
    fun `isDark with Typical returns true after dusk`() {
        mockSkylightRepository { dummyTypical(Date(it.time - 4*timeDifferenceMillis)) }

        assertTrue(mockSkylightRepository.isDark(dummyCoordinates, dummyDate))
    }
    //endregion

    private fun mockSkylightRepository(returnFunction: (Date) -> SkylightInfo) {
        mockSkylightRepository = mock {
            on { determineSkylightInfo(any(), any()) } doAnswer { invocation ->
                returnFunction(invocation.getArgument(1))
            }
        }
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

}
