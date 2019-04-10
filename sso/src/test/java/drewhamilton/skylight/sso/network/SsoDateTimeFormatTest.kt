package drewhamilton.skylight.sso.network

import drewhamilton.skylight.sso.network.SsoDateTimeFormat
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.ParseException
import java.util.*

class SsoDateTimeFormatTest {

    // 1999 Dec. 31 11:59:59.000pm UTC:
    private val testDateTime = Date(946_684_799_000)

    private val testDateTimeStringZ = "1999-12-31T23:59:59Z"
    private val testDateTimeStringUtc = "1999-12-31T23:59:59+00:00"
    private val testDateTimeStringCest = "2000-01-01T01:59:59+02:00"

    private val ssoDateTimeFormat = SsoDateTimeFormat()

    @Test
    fun `format returns string in UTC`() {
        assertEquals(testDateTimeStringUtc, ssoDateTimeFormat.format(testDateTime))
    }

    @Test
    fun `parse with Z time zone returns date from UTC string`() {
        assertEquals(testDateTime, ssoDateTimeFormat.parse(testDateTimeStringZ))
    }

    @Test
    fun `parse with UTC time zone returns date from UTC string`() {
        assertEquals(testDateTime, ssoDateTimeFormat.parse(testDateTimeStringUtc))
    }

    @Test
    fun `parse with CEST time zone returns date from CEST string`() {
        assertEquals(testDateTime, ssoDateTimeFormat.parse(testDateTimeStringCest))
    }

    @Test(expected = ParseException::class)
    fun `parse without time zone colon throws ParseException`() {
        ssoDateTimeFormat.parse("2017-03-31T07:48:23+0200")
    }
}
