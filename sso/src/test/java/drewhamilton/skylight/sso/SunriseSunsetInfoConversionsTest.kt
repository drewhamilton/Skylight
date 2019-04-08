package drewhamilton.skylight.sso

import drewhamilton.skylight.AlwaysDaytime
import drewhamilton.skylight.AlwaysLight
import drewhamilton.skylight.NeverDaytime
import drewhamilton.skylight.NeverLight
import drewhamilton.skylight.Typical
import drewhamilton.skylight.sso.network.ApiConstants
import drewhamilton.skylight.sso.network.models.SunriseSunsetInfo
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class SunriseSunsetInfoConversionsTest {

    private val throwaway = Date(0)

    @Test
    fun `toSkylightInfo with twilight begin NONE and sunrise NONE returns NeverLight`() {
        val input = SunriseSunsetInfo(ApiConstants.DATE_TIME_NONE, throwaway, ApiConstants.DATE_TIME_NONE, throwaway)
        val output = input.toSkylightInfo()
        assertEquals(NeverLight(), output)
    }

    @Test
    fun `toSkylightInfo with twilight begin ALWAYS_DAY and sunrise ALWAYS_DAY returns AlwaysDaytime`() {
        val input = SunriseSunsetInfo(
            ApiConstants.DATE_TIME_ALWAYS_DAY,
            throwaway,
            ApiConstants.DATE_TIME_ALWAYS_DAY,
            throwaway
        )
        val output = input.toSkylightInfo()
        assertEquals(AlwaysDaytime(), output)
    }

    @Test
    fun `toSkylightInfo with only twilight begin NONE returns AlwaysLight`() {
        val sunrise = Date(2)
        val sunset = Date(3)
        val input = SunriseSunsetInfo(sunrise, sunset, ApiConstants.DATE_TIME_NONE, throwaway)
        val output = input.toSkylightInfo()
        assertEquals(AlwaysLight(sunrise, sunset), output)
    }

    @Test
    fun `toSkylightInfo with only sunrise NONE returns NeverDaytime`() {
        val dawn = Date(1)
        val dusk = Date(4)
        val input = SunriseSunsetInfo(ApiConstants.DATE_TIME_NONE, throwaway, dawn, dusk)
        val output = input.toSkylightInfo()
        assertEquals(NeverDaytime(dawn, dusk), output)
    }

    @Test
    fun `toSkylightInfo with no special inputs returns Typical`() {
        val dawn = Date(1)
        val sunrise = Date(2)
        val sunset = Date(3)
        val dusk = Date(4)
        val input = SunriseSunsetInfo(sunrise, sunset, dawn, dusk)
        val output = input.toSkylightInfo()
        assertEquals(Typical(dawn, sunrise, sunset, dusk), output)
    }
}
