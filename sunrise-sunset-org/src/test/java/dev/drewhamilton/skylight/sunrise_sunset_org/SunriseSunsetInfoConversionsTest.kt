package dev.drewhamilton.skylight.sunrise_sunset_org

import dev.drewhamilton.skylight.SkylightDay
import dev.drewhamilton.skylight.sunrise_sunset_org.network.ApiConstants
import dev.drewhamilton.skylight.sunrise_sunset_org.network.response.SunriseSunsetInfo
import java.time.LocalDate
import java.time.ZonedDateTime
import org.junit.Assert.assertEquals
import org.junit.Test

class SunriseSunsetInfoConversionsTest {

    private val dateString = "2011-04-12"
    private val testDate = LocalDate.parse(dateString)
    private val throwaway = ""

    @Test fun `toSkylightDay with twilight begin NONE and sunrise NONE returns NeverLight`() {
        val input = SunriseSunsetInfo(
            ApiConstants.DATE_TIME_NONE,
            throwaway,
            ApiConstants.DATE_TIME_NONE,
            throwaway
        )
        val output = input.toSkylightDay(testDate)
        assertEquals(SkylightDay.NeverLight(date = testDate), output)
    }

    @Test fun `toSkylightDay with twilight begin ALWAYS_DAY and sunrise ALWAYS_DAY returns AlwaysDaytime`() {
        val input = SunriseSunsetInfo(
            ApiConstants.DATE_TIME_ALWAYS_DAY,
            throwaway,
            ApiConstants.DATE_TIME_ALWAYS_DAY,
            throwaway
        )
        val output = input.toSkylightDay(testDate)
        assertEquals(SkylightDay.AlwaysDaytime(date = testDate), output)
    }

    @Test fun `toSkylightDay with only twilight begin NONE returns AlwaysLight`() {
        val sunriseTimeString = "07:01:23+00:00"
        val testSunrise = dateTimeString(sunriseTimeString)
        val sunsetTimeString = "18:54:32+00:00"
        val testSunset = dateTimeString(sunsetTimeString)

        val input = SunriseSunsetInfo(testSunrise, testSunset, ApiConstants.DATE_TIME_NONE, ApiConstants.DATE_TIME_NONE)
        val output = input.toSkylightDay(testDate)

        val expected = SkylightDay.Typical(
            date = testDate,
            sunrise = ZonedDateTime.parse(testSunrise).toInstant(),
            sunset = ZonedDateTime.parse(testSunset).toInstant()
        )
        assertEquals(expected, output)
    }

    @Test fun `toSkylightDay with only sunrise NONE returns NeverDaytime`() {
        val dawnTimeString = "06:01:23+00:00"
        val testDawn = dateTimeString(dawnTimeString)
        val duskTimeString = "18:54:32+00:00"
        val testDusk = dateTimeString(duskTimeString)

        val input = SunriseSunsetInfo(ApiConstants.DATE_TIME_NONE, ApiConstants.DATE_TIME_NONE, testDawn, testDusk)
        val output = input.toSkylightDay(testDate)

        val expected = SkylightDay.Typical(
            date = testDate,
            dawn = ZonedDateTime.parse(testDawn).toInstant(),
            dusk = ZonedDateTime.parse(testDusk).toInstant()
        )
        assertEquals(expected, output)
    }

    @Test fun `toSkylightDay with no special inputs returns Typical`() {
        val dawnTimeString = "06:01:23+00:00"
        val testDawn = dateTimeString(dawnTimeString)
        val sunriseTimeString = "07:01:23+00:00"
        val testSunrise = dateTimeString(sunriseTimeString)
        val sunsetTimeString = "18:54:32+00:00"
        val testSunset = dateTimeString(sunsetTimeString)
        val duskTimeString = "18:54:32+00:00"
        val testDusk = dateTimeString(duskTimeString)

        val input = SunriseSunsetInfo(testSunrise, testSunset, testDawn, testDusk)
        val output = input.toSkylightDay(testDate)

        val expected = SkylightDay.Typical(
            date = testDate,
            dawn = ZonedDateTime.parse(testDawn).toInstant(),
            sunrise = ZonedDateTime.parse(testSunrise).toInstant(),
            sunset = ZonedDateTime.parse(testSunset).toInstant(),
            dusk = ZonedDateTime.parse(testDusk).toInstant()
        )
        assertEquals(expected, output)
    }

    private fun dateTimeString(timeString: String) = "${dateString}T$timeString"
}
