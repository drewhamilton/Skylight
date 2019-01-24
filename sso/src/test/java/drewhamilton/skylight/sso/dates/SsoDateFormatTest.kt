package drewhamilton.skylight.sso.dates

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class SsoDateFormatTest {

    private val expectedJavaDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private val expectedTimeZone = TimeZone.getTimeZone("UTC")

    private val dummyDateString = "2016-01-23"
    private val dummyDate: Date

    private val ssoDateFormat = SsoDateFormat()

    init {
        expectedJavaDateFormat.timeZone = expectedTimeZone
        dummyDate = expectedJavaDateFormat.parse(dummyDateString)
    }

    @Test
    fun `format produces expected string`() {
        assertEquals(dummyDateString, ssoDateFormat.format(dummyDate))
    }

    @Test
    fun `parse produces expected date`() {
        assertEquals(dummyDate, ssoDateFormat.parse(dummyDateString))
    }
}
