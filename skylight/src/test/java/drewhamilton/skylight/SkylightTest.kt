package drewhamilton.skylight

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.Date

class SkylightTest {

    private val timeDifferenceMillis = 5000L

    private val dummyCoordinates = Coordinates(50.0, 60.0)
    private val dummyDate = Date(9876543210L)

    private lateinit var mockSkylight: Skylight

    //region isLight
    @Test
    fun `isLight with AlwaysDaytime returns true`() {
        mockSkylight { dummyAlwaysDaytime() }

        assertTrue(mockSkylight.isLight(dummyCoordinates, dummyDate))
    }

    @Test
    fun `isLight with AlwaysLight returns true`() {
        mockSkylight { dummyAlwaysLight(it) }

        assertTrue(mockSkylight.isLight(dummyCoordinates, dummyDate))
    }

    @Test
    fun `isLight with NeverLight returns false`() {
        mockSkylight { dummyNeverLight() }

        assertFalse(mockSkylight.isLight(dummyCoordinates, dummyDate))
    }

    @Test
    fun `isLight with NeverDaytime returns false before dawn`() {
        mockSkylight { dummyNeverDaytime(Date(it.time + timeDifferenceMillis)) }

        assertFalse(mockSkylight.isLight(dummyCoordinates, dummyDate))
    }

    @Test
    fun `isLight with NeverDaytime returns true between dawn and dusk`() {
        mockSkylight { dummyNeverDaytime(Date(it.time - timeDifferenceMillis/2)) }

        assertTrue(mockSkylight.isLight(dummyCoordinates, dummyDate))
    }

    @Test
    fun `isLight with NeverDaytime returns false after dusk`() {
        mockSkylight { dummyNeverDaytime(Date(it.time - 2*timeDifferenceMillis)) }

        assertFalse(mockSkylight.isLight(dummyCoordinates, dummyDate))
    }

    @Test
    fun `isLight with Typical returns false before dawn`() {
        mockSkylight { dummyTypical(Date(it.time + timeDifferenceMillis)) }

        assertFalse(mockSkylight.isLight(dummyCoordinates, dummyDate))
    }

    @Test
    fun `isLight with Typical returns true between dawn and dusk`() {
        mockSkylight { dummyTypical(Date(it.time - timeDifferenceMillis/2)) }

        assertTrue(mockSkylight.isLight(dummyCoordinates, dummyDate))
    }

    @Test
    fun `isLight with Typical returns false after dusk`() {
        mockSkylight { dummyTypical(Date(it.time - 4*timeDifferenceMillis)) }

        assertFalse(mockSkylight.isLight(dummyCoordinates, dummyDate))
    }
    //endregion

    //region isDark
    @Test
    fun `isDark with AlwaysDaytime returns false`() {
        mockSkylight { dummyAlwaysDaytime() }

        assertFalse(mockSkylight.isDark(dummyCoordinates, dummyDate))
    }

    @Test
    fun `isDark with AlwaysLight returns false`() {
        mockSkylight { dummyAlwaysLight(it) }

        assertFalse(mockSkylight.isDark(dummyCoordinates, dummyDate))
    }

    @Test
    fun `isDark with NeverLight returns true`() {
        mockSkylight { dummyNeverLight() }

        assertTrue(mockSkylight.isDark(dummyCoordinates, dummyDate))
    }

    @Test
    fun `isDark with NeverDaytime returns true before dawn`() {
        mockSkylight { dummyNeverDaytime(Date(it.time + timeDifferenceMillis)) }

        assertTrue(mockSkylight.isDark(dummyCoordinates, dummyDate))
    }

    @Test
    fun `isDark with NeverDaytime returns false between dawn and dusk`() {
        mockSkylight { dummyNeverDaytime(Date(it.time - timeDifferenceMillis/2)) }

        assertFalse(mockSkylight.isDark(dummyCoordinates, dummyDate))
    }

    @Test
    fun `isDark with NeverDaytime returns true after dusk`() {
        mockSkylight { dummyNeverDaytime(Date(it.time - 2*timeDifferenceMillis)) }

        assertTrue(mockSkylight.isDark(dummyCoordinates, dummyDate))
    }

    @Test
    fun `isDark with Typical returns true before dawn`() {
        mockSkylight { dummyTypical(Date(it.time + timeDifferenceMillis)) }

        assertTrue(mockSkylight.isDark(dummyCoordinates, dummyDate))
    }

    @Test
    fun `isDark with Typical returns false between dawn and dusk`() {
        mockSkylight { dummyTypical(Date(it.time - timeDifferenceMillis/2)) }

        assertFalse(mockSkylight.isDark(dummyCoordinates, dummyDate))
    }

    @Test
    fun `isDark with Typical returns true after dusk`() {
        mockSkylight { dummyTypical(Date(it.time - 4*timeDifferenceMillis)) }

        assertTrue(mockSkylight.isDark(dummyCoordinates, dummyDate))
    }
    //endregion

    private fun mockSkylight(returnFunction: (Date) -> SkylightDay) {
        mockSkylight = mock {
            on { getSkylightDay(any(), any()) } doAnswer { invocation ->
                returnFunction(invocation.getArgument(1))
            }
        }
    }

    private fun dummyAlwaysDaytime() = SkylightDay.AlwaysDaytime
    private fun dummyAlwaysLight(sunrise: Date) =
        SkylightDay.AlwaysLight(sunrise, Date(sunrise.time + timeDifferenceMillis))
    private fun dummyNeverLight() = SkylightDay.NeverLight
    private fun dummyNeverDaytime(dawn: Date) = SkylightDay.NeverDaytime(dawn, Date(dawn.time + timeDifferenceMillis))

    private fun dummyTypical(dawn: Date) = SkylightDay.Typical(
        dawn,
        Date(dawn.time + timeDifferenceMillis),
        Date(dawn.time + 2 * timeDifferenceMillis),
        Date(dawn.time + 3 * timeDifferenceMillis)
    )

}
