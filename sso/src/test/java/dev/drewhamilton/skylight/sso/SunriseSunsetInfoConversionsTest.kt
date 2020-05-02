package dev.drewhamilton.skylight.sso

import dev.drewhamilton.skylight.SkylightDay
import dev.drewhamilton.skylight.sso.network.ApiConstants
import dev.drewhamilton.skylight.sso.network.response.SunriseSunsetInfo
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.ZonedDateTime

class SunriseSunsetInfoConversionsTest {

    private val dateString = "2011-04-12"
    private val testDate = LocalDate.parse(dateString)
    private val throwaway = ""

    @Test
    fun `toSkylightDay with twilight begin NONE and sunrise NONE returns NeverLight`() {
        val input = SunriseSunsetInfo(
            ApiConstants.DATE_TIME_NONE,
            throwaway,
            ApiConstants.DATE_TIME_NONE,
            throwaway
        )
        val output = input.toSkylightDay(testDate, ZoneOffset.UTC)
        assertEquals(SkylightDay.NeverLight { date = testDate }, output)
    }

    @Test
    fun `toSkylightDay with twilight begin ALWAYS_DAY and sunrise ALWAYS_DAY returns AlwaysDaytime`() {
        val input = SunriseSunsetInfo(
            ApiConstants.DATE_TIME_ALWAYS_DAY,
            throwaway,
            ApiConstants.DATE_TIME_ALWAYS_DAY,
            throwaway
        )
        val output = input.toSkylightDay(testDate, ZoneOffset.UTC)
        assertEquals(SkylightDay.AlwaysDaytime { date = testDate }, output)
    }

    @Test
    fun `toSkylightDay with only twilight begin NONE returns AlwaysLight`() {
        val sunriseTimeString = "07:01:23+00:00"
        val testSunrise = dateTimeString(sunriseTimeString)
        val sunsetTimeString = "18:54:32+00:00"
        val testSunset = dateTimeString(sunsetTimeString)

        val input = SunriseSunsetInfo(testSunrise, testSunset, ApiConstants.DATE_TIME_NONE, ApiConstants.DATE_TIME_NONE)
        val output = input.toSkylightDay(testDate, ZoneOffset.UTC)

        val expected = SkylightDay.Typical {
            date = testDate
            sunrise = ZonedDateTime.parse(testSunrise)
            sunset = ZonedDateTime.parse(testSunset)
        }
        assertEquals(expected, output)
    }

    @Test
    fun `toSkylightDay with only sunrise NONE returns NeverDaytime`() {
        val dawnTimeString = "06:01:23+00:00"
        val testDawn = dateTimeString(dawnTimeString)
        val duskTimeString = "18:54:32+00:00"
        val testDusk = dateTimeString(duskTimeString)

        val input = SunriseSunsetInfo(ApiConstants.DATE_TIME_NONE, ApiConstants.DATE_TIME_NONE, testDawn, testDusk)
        val output = input.toSkylightDay(testDate, ZoneOffset.UTC)

        val expected = SkylightDay.Typical {
            date = testDate
            dawn = ZonedDateTime.parse(testDawn)
            dusk = ZonedDateTime.parse(testDusk)
        }
        assertEquals(expected, output)
    }

    @Test
    fun `toSkylightDay with no special inputs returns Typical`() {
        val dawnTimeString = "06:01:23+00:00"
        val testDawn = dateTimeString(dawnTimeString)
        val sunriseTimeString = "07:01:23+00:00"
        val testSunrise = dateTimeString(sunriseTimeString)
        val sunsetTimeString = "18:54:32+00:00"
        val testSunset = dateTimeString(sunsetTimeString)
        val duskTimeString = "18:54:32+00:00"
        val testDusk = dateTimeString(duskTimeString)

        val input = SunriseSunsetInfo(testSunrise, testSunset, testDawn, testDusk)
        val output = input.toSkylightDay(testDate, ZoneOffset.UTC)

        val expected = SkylightDay.Typical {
            date = testDate
            dawn = ZonedDateTime.parse(testDawn)
            sunrise = ZonedDateTime.parse(testSunrise)
            sunset = ZonedDateTime.parse(testSunset)
            dusk = ZonedDateTime.parse(testDusk)
        }
        assertEquals(expected, output)
    }

    private fun dateTimeString(timeString: String) = "${dateString}T$timeString"
}
