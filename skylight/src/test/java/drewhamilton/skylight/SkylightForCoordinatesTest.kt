package drewhamilton.skylight

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.Month
import java.time.OffsetTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import kotlin.reflect.KClass

class SkylightForCoordinatesTest {

    private lateinit var mockSkylight: Skylight
    private val testCoordinates = Coordinates(98.7, 6.54)
    private val testTime = OffsetTime.of(15, 0, 0, 0, ZoneOffset.UTC)

    private lateinit var skylightForCoordinates: SkylightForCoordinates

    @Before
    fun setUp() {
        mockSkylight = mock()
        skylightForCoordinates = mockSkylight.forCoordinates(testCoordinates)
    }

    @Test
    fun `getSkylightDay passes coordinates and date to Skylight`() {
        val testDate = LocalDate.of(2019, Month.MAY, 20)
        skylightForCoordinates.getSkylightDay(testDate)

        verify(mockSkylight).getSkylightDay(testCoordinates, testDate)
        verifyNoMoreInteractions(mockSkylight)
    }

    @Test
    fun `isLight forwards to Skylight isLight`() {
        val testDate = LocalDate.of(2019, Month.MAY, 20)
        val testInputDateTime = ZonedDateTime.of(testDate, testTime.toLocalTime(), testTime.offset)
        val testCoordinates = Coordinates(1.23, 45.6)
        for (kClass in SkylightDay::class.sealedSubclasses) {
            whenever(mockSkylight.getSkylightDay(any(), any<LocalDate>())).thenReturn(kClass.instantiate(testDate))
            assertEquals(
                mockSkylight.isLight(testCoordinates, testInputDateTime),
                skylightForCoordinates.isLight(testInputDateTime)
            )
        }
    }

    @Test
    fun `isDark forwards to Skylight isDark`() {
        val testDate = LocalDate.of(2019, Month.MAY, 20)
        val testInputDateTime = ZonedDateTime.of(testDate, testTime.toLocalTime(), testTime.offset)
        val testCoordinates = Coordinates(12.3, 4.56)
        for (kClass in SkylightDay::class.sealedSubclasses) {
            whenever(mockSkylight.getSkylightDay(any(), any<LocalDate>())).thenReturn(kClass.instantiate(testDate))
            assertEquals(
                mockSkylight.isDark(testCoordinates, testInputDateTime),
                skylightForCoordinates.isDark(testInputDateTime)
            )
        }
    }

    private fun KClass<out SkylightDay>.instantiate(date: LocalDate) = when(this) {
        SkylightDay.Typical::class -> SkylightDay.Typical(date, testTime, testTime, testTime, testTime)
        SkylightDay.AlwaysDaytime::class -> SkylightDay.AlwaysDaytime(date)
        SkylightDay.AlwaysLight::class -> SkylightDay.AlwaysLight(date, testTime, testTime)
        SkylightDay.NeverDaytime::class -> SkylightDay.NeverDaytime(date, testTime, testTime)
        SkylightDay.NeverLight::class -> SkylightDay.NeverLight(date)
        else -> throw IllegalArgumentException("Unknown ${SkylightDay::class} subtype: $this")
    }
}
