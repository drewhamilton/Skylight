package drewhamilton.skylight.sso.serialization

import drewhamilton.skylight.sso.dates.SsoDateFormat
import drewhamilton.skylight.sso.dates.SsoDateTimeFormat
import org.junit.Assert.assertEquals
import org.junit.Test

class SsoDateTimeAdapterTest {

    private val dummyDateTimeString = "2019-01-23T17:28:39+00:00"
    private val dummyDateTime = SsoDateTimeFormat().parse(dummyDateTimeString)

    private val dummyDateString = "2019-01-23"
    private val dummyDate = SsoDateFormat().parse(dummyDateString)

    private var ssoDateTimeAdapter = SsoDateTimeAdapter()

    @Test
    fun `dateTimeToString returns string in expected format`() {
        assertEquals(dummyDateTimeString, ssoDateTimeAdapter.dateTimeToString(dummyDateTime))
    }

    @Test
    fun `dateTimeFromString with valid input returns corresponding date`() {
        assertEquals(dummyDateTime, ssoDateTimeAdapter.dateTimeFromString(dummyDateTimeString))
    }

    @Test
    fun `dateToString returns string in expected format`() {
        assertEquals(dummyDateString, ssoDateTimeAdapter.dateToString(dummyDate))
    }

    @Test
    fun `dateFromString with valid input returns corresponding date`() {
        assertEquals(dummyDate, ssoDateTimeAdapter.dateFromString(dummyDateString))
    }
}
