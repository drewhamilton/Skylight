package drewhamilton.skylight

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Date
import kotlin.reflect.KClass

class SkylightForCoordinatesTest {

    private lateinit var mockSkylight: Skylight
    private val testCoordinates = Coordinates(98.7, 6.54)

    private lateinit var skylightForCoordinates: SkylightForCoordinates

    @Before
    fun setUp() {
        mockSkylight = mock()
        skylightForCoordinates = mockSkylight.forCoordinates(testCoordinates)
    }

    @Test
    fun `getSkylightDay passes coordinates and date to Skylight`() {
        val testDate = Date(54321)
        skylightForCoordinates.getSkylightDay(testDate)

        verify(mockSkylight).getSkylightDay(testCoordinates, testDate)
        verifyNoMoreInteractions(mockSkylight)
    }

    @Test
    fun `isLight forwards to Skylight isLight`() {
        val testInputDate = Date(29385723847)
        val testCoordinates = Coordinates(1.23, 45.6)
        for (kClass in SkylightDay::class.sealedSubclasses) {
            whenever(mockSkylight.getSkylightDay(any(), any<Date>())).thenReturn(kClass.instantiate(Date(234234)))
            assertEquals(
                mockSkylight.isLight(testCoordinates, testInputDate),
                skylightForCoordinates.isLight(testInputDate)
            )
        }
    }

    @Test
    fun `isDark forwards to Skylight isDark`() {
        val testInputDate = Date(239487)
        val testCoordinates = Coordinates(12.3, 4.56)
        for (kClass in SkylightDay::class.sealedSubclasses) {
            whenever(mockSkylight.getSkylightDay(any(), any<Date>())).thenReturn(kClass.instantiate(Date(9823743)))
            assertEquals(
                mockSkylight.isDark(testCoordinates, testInputDate),
                skylightForCoordinates.isDark(testInputDate)
            )
        }
    }

    private fun KClass<out SkylightDay>.instantiate(date: Date) = when(this) {
        SkylightDay.Typical::class -> SkylightDay.Typical(date, date, date, date)
        SkylightDay.AlwaysDaytime::class -> SkylightDay.AlwaysDaytime
        SkylightDay.AlwaysLight::class -> SkylightDay.AlwaysLight(date, date)
        SkylightDay.NeverDaytime::class -> SkylightDay.NeverDaytime(date, date)
        SkylightDay.NeverLight::class -> SkylightDay.NeverLight
        else -> throw IllegalArgumentException("Unknown ${SkylightDay::class} subtype: $this")
    }
}
