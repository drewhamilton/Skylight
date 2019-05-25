package drewhamilton.skylight.backport.sso.network

import org.junit.Assert.assertEquals
import org.junit.Test
import org.threeten.bp.LocalDate

class SsoDateFormatTest {

    private val testDateString = "2016-01-23"
    private val testDate = LocalDate.parse(testDateString)

    @Test
    fun `format produces expected string`() {
        assertEquals(testDateString, testDate.toSsoDateString())
    }
}
