package drewhamilton.skylight.backport.sso

import drewhamilton.skylight.backport.SkylightDayBackport
import drewhamilton.skylight.sso.network.ApiConstants
import drewhamilton.skylight.sso.network.response.SunriseSunsetInfo
import org.junit.Assert.assertEquals
import org.junit.Test
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetTime

class SunriseSunsetInfoConversionBackportsTest {

    private val dateString = "2011-04-12"
    private val date = LocalDate.parse(dateString)
    private val throwaway = ""

    @Test
    fun `toSkylightDay with twilight begin NONE and sunrise NONE returns NeverLight`() {
        val input = SunriseSunsetInfo(
            ApiConstants.DATE_TIME_NONE,
            throwaway,
            ApiConstants.DATE_TIME_NONE,
            throwaway
        )
        val output = input.toSkylightDayBackport(date)
        assertEquals(SkylightDayBackport.NeverLight(date), output)
    }

    @Test
    fun `toSkylightDay with twilight begin ALWAYS_DAY and sunrise ALWAYS_DAY returns AlwaysDaytime`() {
        val input = SunriseSunsetInfo(
            ApiConstants.DATE_TIME_ALWAYS_DAY,
            throwaway,
            ApiConstants.DATE_TIME_ALWAYS_DAY,
            throwaway
        )
        val output = input.toSkylightDayBackport(date)
        assertEquals(SkylightDayBackport.AlwaysDaytime(date), output)
    }

    @Test
    fun `toSkylightDay with only twilight begin NONE returns AlwaysLight`() {
        val sunriseTimeString = "07:01:23+00:00"
        val sunrise = dateTimeString(sunriseTimeString)
        val sunsetTimeString = "18:54:32+00:00"
        val sunset = dateTimeString(sunsetTimeString)

        val input = SunriseSunsetInfo(sunrise, sunset, ApiConstants.DATE_TIME_NONE, throwaway)
        val output = input.toSkylightDayBackport(date)

        val expected = SkylightDayBackport.AlwaysLight(
            date,
            OffsetTime.parse(sunriseTimeString),
            OffsetTime.parse(sunsetTimeString)
        )
        assertEquals(expected, output)
    }

    @Test
    fun `toSkylightDay with only sunrise NONE returns NeverDaytime`() {
        val dawnTimeString = "06:01:23+00:00"
        val dawn = dateTimeString(dawnTimeString)
        val duskTimeString = "18:54:32+00:00"
        val dusk = dateTimeString(duskTimeString)

        val input = SunriseSunsetInfo(ApiConstants.DATE_TIME_NONE, throwaway, dawn, dusk)
        val output = input.toSkylightDayBackport(date)

        val expected = SkylightDayBackport.NeverDaytime(
            date,
            OffsetTime.parse(dawnTimeString),
            OffsetTime.parse(duskTimeString)
        )
        assertEquals(expected, output)
    }

    @Test
    fun `toSkylightDay with no special inputs returns Typical`() {
        val dawnTimeString = "06:01:23+00:00"
        val dawn = dateTimeString(dawnTimeString)
        val sunriseTimeString = "07:01:23+00:00"
        val sunrise = dateTimeString(sunriseTimeString)
        val sunsetTimeString = "18:54:32+00:00"
        val sunset = dateTimeString(sunsetTimeString)
        val duskTimeString = "18:54:32+00:00"
        val dusk = dateTimeString(duskTimeString)

        val input = SunriseSunsetInfo(sunrise, sunset, dawn, dusk)
        val output = input.toSkylightDayBackport(date)

        val expected = SkylightDayBackport.Typical(
            date,
            OffsetTime.parse(dawnTimeString),
            OffsetTime.parse(sunriseTimeString),
            OffsetTime.parse(sunsetTimeString),
            OffsetTime.parse(duskTimeString)
        )
        assertEquals(expected, output)
    }

    private fun dateTimeString(timeString: String) = "${dateString}T$timeString"
}
