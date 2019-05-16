package drewhamilton.skylight.sso

import drewhamilton.skylight.NewSkylightDay
import drewhamilton.skylight.sso.network.ApiConstants
import drewhamilton.skylight.sso.network.response.SunriseSunsetInfo
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.ZonedDateTime

class SunriseSunsetInfoConversionsTest {

    private val date = LocalDate.ofEpochDay(0)
    private val throwaway = ZonedDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC)

    @Test
    fun `toSkylightDay with twilight begin NONE and sunrise NONE returns NeverLight`() {
        val input = SunriseSunsetInfo(
            ApiConstants.DATE_TIME_NONE,
            throwaway,
            ApiConstants.DATE_TIME_NONE,
            throwaway
        )
        val output = input.toSkylightDay(date)
        assertEquals(NewSkylightDay.NeverLight(date), output)
    }

    @Test
    fun `toSkylightDay with twilight begin ALWAYS_DAY and sunrise ALWAYS_DAY returns AlwaysDaytime`() {
        val input = SunriseSunsetInfo(
            ApiConstants.DATE_TIME_ALWAYS_DAY,
            throwaway,
            ApiConstants.DATE_TIME_ALWAYS_DAY,
            throwaway
        )
        val output = input.toSkylightDay(date)
        assertEquals(NewSkylightDay.AlwaysDaytime(date), output)
    }

    @Test
    fun `toSkylightDay with only twilight begin NONE returns AlwaysLight`() {
        val sunrise = ZonedDateTime.ofInstant(Instant.ofEpochMilli(2), ZoneOffset.UTC)
        val sunset = ZonedDateTime.ofInstant(Instant.ofEpochMilli(3), ZoneOffset.UTC)
        val input = SunriseSunsetInfo(sunrise, sunset, ApiConstants.DATE_TIME_NONE, throwaway)
        val output = input.toSkylightDay(date)
        assertEquals(NewSkylightDay.AlwaysLight(date, sunrise.toOffsetTime(), sunset.toOffsetTime()), output)
    }

    @Test
    fun `toSkylightDay with only sunrise NONE returns NeverDaytime`() {
        val dawn = ZonedDateTime.ofInstant(Instant.ofEpochMilli(1), ZoneOffset.UTC)
        val dusk = ZonedDateTime.ofInstant(Instant.ofEpochMilli(4), ZoneOffset.UTC)
        val input = SunriseSunsetInfo(ApiConstants.DATE_TIME_NONE, throwaway, dawn, dusk)
        val output = input.toSkylightDay(date)
        assertEquals(NewSkylightDay.NeverDaytime(date, dawn.toOffsetTime(), dusk.toOffsetTime()), output)
    }

    @Test
    fun `toSkylightDay with no special inputs returns Typical`() {
        val dawn = ZonedDateTime.ofInstant(Instant.ofEpochMilli(1), ZoneOffset.UTC)
        val sunrise = ZonedDateTime.ofInstant(Instant.ofEpochMilli(2), ZoneOffset.UTC)
        val sunset = ZonedDateTime.ofInstant(Instant.ofEpochMilli(3), ZoneOffset.UTC)
        val dusk = ZonedDateTime.ofInstant(Instant.ofEpochMilli(4), ZoneOffset.UTC)
        val input = SunriseSunsetInfo(sunrise, sunset, dawn, dusk)
        val output = input.toSkylightDay(date)
        assertEquals(NewSkylightDay.Typical(
            date,
            dawn.toOffsetTime(),
            sunrise.toOffsetTime(),
            sunset.toOffsetTime(),
            dusk.toOffsetTime()
        ), output)
    }
}
