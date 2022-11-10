package dev.drewhamilton.skylight.sunrise_sunset_org.network

import okhttp3.HttpUrl
import org.junit.Assert.assertEquals
import org.junit.Test

class ApiConstantsTest {

    @Test fun `BASE_URL points to sunrise-sunset_org API`() {
        assertEquals(HttpUrl.get("https://api.sunrise-sunset.org/"), ApiConstants.BASE_URL)
    }

    @Test fun `DATE_TIME_NONE is 1 Jan 1970 at midnight`() {
        assertEquals("1970-01-01T00:00:00+00:00", ApiConstants.DATE_TIME_NONE)
    }

    @Test fun `DATE_TIME_ALWAYS_DAY is 1 Jan 1970 at 1 second after midnight`() {
        assertEquals("1970-01-01T00:00:01+00:00", ApiConstants.DATE_TIME_ALWAYS_DAY)
    }
}
