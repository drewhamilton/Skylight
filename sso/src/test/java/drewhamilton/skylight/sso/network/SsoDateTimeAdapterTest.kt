package drewhamilton.skylight.sso.network

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate
import java.time.ZonedDateTime

class SsoDateTimeAdapterTest {

    private val testDateTimeString = "2019-01-23T17:28:39+00:00"
    private val testDateTime = ZonedDateTime.parse(testDateTimeString, SsoDateTimeFormatters.DATE_TIME)

    private val testDateString = "2019-01-23"
    private val testDate = LocalDate.parse(testDateString, SsoDateTimeFormatters.DATE)

    private var ssoDateTimeAdapter = SsoDateTimeAdapter()

    @Test
    fun `dateTimeToString returns string in expected format`() {
        assertEquals(testDateTimeString, ssoDateTimeAdapter.dateTimeToString(testDateTime))
    }

    @Test
    fun `dateTimeFromString with valid input returns corresponding date`() {
        assertEquals(testDateTime, ssoDateTimeAdapter.dateTimeFromString(testDateTimeString))
    }

    @Test
    fun `dateToString returns string in expected format`() {
        assertEquals(testDateString, ssoDateTimeAdapter.dateToString(testDate))
    }

    @Test
    fun `dateFromString with valid input returns corresponding date`() {
        assertEquals(testDate, ssoDateTimeAdapter.dateFromString(testDateString))
    }
}
