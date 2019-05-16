package drewhamilton.skylight.sso.network

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeParseException

class SsoDateTimeFormattersTest {

    private val testDateString = "2016-01-23"
    private val testDate = LocalDate.parse(testDateString)

    // 1999 Dec. 31 11:59:59.000pm UTC:
    private val testDateTime = ZonedDateTime.of(1999, 12, 31, 23, 59, 59, 0, ZoneOffset.UTC)

    private val testDateTimeStringUtc = "1999-12-31T23:59:59+00:00"
    private val testDateTimeStringCest = "2000-01-01T01:59:59+02:00"

    @Test
    fun `format produces expected string`() {
        assertEquals(testDateString, SsoDateTimeFormatters.DATE.format(testDate))
    }

    @Test
    fun `parse produces expected date`() {
        assertEquals(testDate, LocalDate.parse(testDateString, SsoDateTimeFormatters.DATE))
    }

    @Test
    fun `format returns string in UTC`() {
        assertEquals(testDateTimeStringUtc, SsoDateTimeFormatters.DATE_TIME.format(testDateTime))
    }

    @Test
    fun `parse with UTC time zone returns date from UTC string`() {
        assertEquals(testDateTime, ZonedDateTime.parse(testDateTimeStringUtc, SsoDateTimeFormatters.DATE_TIME))
    }

    @Test
    fun `parse with CEST time zone returns date from CEST string`() {
        assertEquals(
            testDateTime.withZoneSameInstant(ZoneOffset.ofHours(2)),
            ZonedDateTime.parse(testDateTimeStringCest, SsoDateTimeFormatters.DATE_TIME)
        )
    }

    @Test(expected = DateTimeParseException::class)
    fun `parse without time zone colon throws ParseException`() {
        ZonedDateTime.parse("2017-03-31T07:48:23+0200", SsoDateTimeFormatters.DATE_TIME)
    }
}
