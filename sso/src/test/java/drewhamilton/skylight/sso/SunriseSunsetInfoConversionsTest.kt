package drewhamilton.skylight.sso

import drewhamilton.skylight.SkylightDay
import drewhamilton.skylight.sso.network.ApiConstants
import drewhamilton.skylight.sso.network.response.SunriseSunsetInfo
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class SunriseSunsetInfoConversionsTest {

    private val throwaway = Date(0)

    @Test
    fun `toSkylightDay with twilight begin NONE and sunrise NONE returns NeverLight`() {
        val input = SunriseSunsetInfo(
            ApiConstants.DATE_TIME_NONE,
            throwaway,
            ApiConstants.DATE_TIME_NONE,
            throwaway
        )
        val output = input.toSkylightDay()
        assertEquals(SkylightDay.NeverLight, output)
    }

    @Test
    fun `toSkylightDay with twilight begin ALWAYS_DAY and sunrise ALWAYS_DAY returns AlwaysDaytime`() {
        val input = SunriseSunsetInfo(
            ApiConstants.DATE_TIME_ALWAYS_DAY,
            throwaway,
            ApiConstants.DATE_TIME_ALWAYS_DAY,
            throwaway
        )
        val output = input.toSkylightDay()
        assertEquals(SkylightDay.AlwaysDaytime, output)
    }

    @Test
    fun `toSkylightDay with only twilight begin NONE returns AlwaysLight`() {
        val sunrise = Date(2)
        val sunset = Date(3)
        val input = SunriseSunsetInfo(
            sunrise,
            sunset,
            ApiConstants.DATE_TIME_NONE,
            throwaway
        )
        val output = input.toSkylightDay()
        assertEquals(SkylightDay.AlwaysLight(sunrise, sunset), output)
    }

    @Test
    fun `toSkylightDay with only sunrise NONE returns NeverDaytime`() {
        val dawn = Date(1)
        val dusk = Date(4)
        val input = SunriseSunsetInfo(
            ApiConstants.DATE_TIME_NONE,
            throwaway,
            dawn,
            dusk
        )
        val output = input.toSkylightDay()
        assertEquals(SkylightDay.NeverDaytime(dawn, dusk), output)
    }

    @Test
    fun `toSkylightDay with no special inputs returns Typical`() {
        val dawn = Date(1)
        val sunrise = Date(2)
        val sunset = Date(3)
        val dusk = Date(4)
        val input = SunriseSunsetInfo(sunrise, sunset, dawn, dusk)
        val output = input.toSkylightDay()
        assertEquals(SkylightDay.Typical(dawn, sunrise, sunset, dusk), output)
    }
}
