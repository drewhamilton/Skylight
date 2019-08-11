package drewhamilton.skylight

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import drewhamilton.skylight.backport.Coordinates
import drewhamilton.skylight.backport.Skylight
import drewhamilton.skylight.backport.SkylightDay
import drewhamilton.skylight.backport.isDark
import drewhamilton.skylight.backport.isLight
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.threeten.bp.LocalDate
import org.threeten.bp.Month
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

class SkylightTest {

    private val testCoordinates = Coordinates(50.0, 60.0)
    private val testDate = LocalDate.of(2019, Month.MAY, 15)
    private val testDawn = ZonedDateTime.of(2019, 5, 15, 8, 0, 0, 0, ZoneOffset.UTC)
    private val testSunrise = ZonedDateTime.of(2019, 5, 15, 10, 0, 0, 0, ZoneOffset.UTC)
    private val testSunset = ZonedDateTime.of(2019, 5, 15, 14, 0, 0, 0, ZoneOffset.UTC)
    private val testDusk = ZonedDateTime.of(2019, 5, 15, 16, 0, 0, 0, ZoneOffset.UTC)

    private val beforeDawn = ZonedDateTime.of(testDate, testDawn.minusHours(2).toLocalTime(), ZoneOffset.UTC)
    private val betweenDawnAndDusk = ZonedDateTime.of(testDate, testSunrise.plusHours(2).toLocalTime(), ZoneOffset.UTC)
    private val afterDusk = ZonedDateTime.of(testDate, testDusk.plusHours(2).toLocalTime(), ZoneOffset.UTC)

    private val testAlwaysDaytime = SkylightDay.AlwaysDaytime(testDate)
    private val testAlwaysLight = SkylightDay.AlwaysLight(testSunrise, testSunset)
    private val testNeverLight = SkylightDay.NeverLight(testDate)
    private val testNeverDaytime = SkylightDay.NeverDaytime(testDawn, testDusk)
    private val testTypical = SkylightDay.Typical(testDawn, testSunrise, testSunset, testDusk)

    private lateinit var mockSkylight: Skylight

    //region isLight
    @Test
    fun `isLight with AlwaysDaytime returns true`() {
        mockSkylight(testAlwaysDaytime)

        assertTrue(mockSkylight.isLight(testCoordinates, beforeDawn))
        assertTrue(mockSkylight.isLight(testCoordinates, betweenDawnAndDusk))
        assertTrue(mockSkylight.isLight(testCoordinates, afterDusk))
    }

    @Test
    fun `isLight with AlwaysLight returns true`() {
        mockSkylight(testAlwaysLight)

        assertTrue(mockSkylight.isLight(testCoordinates, beforeDawn))
        assertTrue(mockSkylight.isLight(testCoordinates, betweenDawnAndDusk))
        assertTrue(mockSkylight.isLight(testCoordinates, afterDusk))
    }

    @Test
    fun `isLight with NeverLight returns false`() {
        mockSkylight(testNeverLight)

        assertFalse(mockSkylight.isLight(testCoordinates, beforeDawn))
        assertFalse(mockSkylight.isLight(testCoordinates, betweenDawnAndDusk))
        assertFalse(mockSkylight.isLight(testCoordinates, afterDusk))
    }

    @Test
    fun `isLight with NeverDaytime returns false before dawn`() {
        mockSkylight(testNeverDaytime)

        assertFalse(mockSkylight.isLight(testCoordinates, beforeDawn))
    }

    @Test
    fun `isLight with NeverDaytime returns true between dawn and dusk`() {
        mockSkylight(testNeverDaytime)

        assertTrue(mockSkylight.isLight(testCoordinates, betweenDawnAndDusk))
    }

    @Test
    fun `isLight with NeverDaytime returns false after dusk`() {
        mockSkylight(testNeverDaytime)

        assertFalse(mockSkylight.isLight(testCoordinates, afterDusk))
    }

    @Test
    fun `isLight with Typical returns false before dawn`() {
        mockSkylight(testTypical)

        assertFalse(mockSkylight.isLight(testCoordinates, beforeDawn))
    }

    @Test
    fun `isLight with Typical returns true between dawn and dusk`() {
        mockSkylight(testTypical)

        assertTrue(mockSkylight.isLight(testCoordinates, betweenDawnAndDusk))
    }

    @Test
    fun `isLight with Typical returns false after dusk`() {
        mockSkylight(testTypical)

        assertFalse(mockSkylight.isLight(testCoordinates, afterDusk))
    }
    //endregion

    //region isDark
    @Test
    fun `isDark with AlwaysDaytime returns false`() {
        mockSkylight(testAlwaysDaytime)

        assertFalse(mockSkylight.isDark(testCoordinates, beforeDawn))
        assertFalse(mockSkylight.isDark(testCoordinates, betweenDawnAndDusk))
        assertFalse(mockSkylight.isDark(testCoordinates, afterDusk))
    }

    @Test
    fun `isDark with AlwaysLight returns false`() {
        mockSkylight(testAlwaysLight)

        assertFalse(mockSkylight.isDark(testCoordinates, beforeDawn))
        assertFalse(mockSkylight.isDark(testCoordinates, betweenDawnAndDusk))
        assertFalse(mockSkylight.isDark(testCoordinates, afterDusk))
    }

    @Test
    fun `isDark with NeverLight returns true`() {
        mockSkylight(testNeverLight)

        assertTrue(mockSkylight.isDark(testCoordinates, beforeDawn))
        assertTrue(mockSkylight.isDark(testCoordinates, betweenDawnAndDusk))
        assertTrue(mockSkylight.isDark(testCoordinates, afterDusk))
    }

    @Test
    fun `isDark with NeverDaytime returns true before dawn`() {
        mockSkylight(testNeverDaytime)

        assertTrue(mockSkylight.isDark(testCoordinates, beforeDawn))
    }

    @Test
    fun `isDark with NeverDaytime returns false between dawn and dusk`() {
        mockSkylight(testNeverDaytime)

        assertFalse(mockSkylight.isDark(testCoordinates, betweenDawnAndDusk))
    }

    @Test
    fun `isDark with NeverDaytime returns true after dusk`() {
        mockSkylight(testNeverDaytime)

        assertTrue(mockSkylight.isDark(testCoordinates, afterDusk))
    }

    @Test
    fun `isDark with Typical returns true before dawn`() {
        mockSkylight(testTypical)

        assertTrue(mockSkylight.isDark(testCoordinates, beforeDawn))
    }

    @Test
    fun `isDark with Typical returns false between dawn and dusk`() {
        mockSkylight(testTypical)

        assertFalse(mockSkylight.isDark(testCoordinates, betweenDawnAndDusk))
    }

    @Test
    fun `isDark with Typical returns true after dusk`() {
        mockSkylight(testTypical)

        assertTrue(mockSkylight.isDark(testCoordinates, afterDusk))
    }
    //endregion

    private fun mockSkylight(skylightDay: SkylightDay) {
        mockSkylight = mock {
            on { getSkylightDay(any(), any()) } doReturn skylightDay
        }
    }
}
