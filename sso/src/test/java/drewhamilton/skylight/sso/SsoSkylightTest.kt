package drewhamilton.skylight.sso

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import drewhamilton.skylight.Coordinates
import drewhamilton.skylight.sso.network.InfoClient
import drewhamilton.skylight.sso.network.request.Params
import drewhamilton.skylight.sso.network.response.SunriseSunsetInfo
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Instant
import java.time.LocalDate
import java.time.Month
import java.time.ZoneOffset
import java.time.ZonedDateTime

class SsoSkylightTest {

    private val testDawn = ZonedDateTime.ofInstant(Instant.ofEpochMilli(99_999_999_910_000L), ZoneOffset.UTC)
    private val testSunrise = ZonedDateTime.ofInstant(Instant.ofEpochMilli(99_999_999_920_000L), ZoneOffset.UTC)
    private val testSunset = ZonedDateTime.ofInstant(Instant.ofEpochMilli(99_999_999_930_000L), ZoneOffset.UTC)
    private val testDusk = ZonedDateTime.ofInstant(Instant.ofEpochMilli(99_999_999_940_000L), ZoneOffset.UTC)

    private val testLatitude = 12.3
    private val testLongitude = 45.6
    private val testToday = LocalDate.of(2019, Month.MAY, 16)
    private val testCoordinates = Coordinates(testLatitude, testLongitude)
    private val testParams = Params(testLatitude, testLongitude, testToday)

    private val testSunriseSunsetInfo = SunriseSunsetInfo(testDawn, testSunrise, testSunset, testDusk)

    private lateinit var mockClient: InfoClient

    @Test
    fun `getSkylightDay returns converted info from client`() {
        mockClient = mock {
            on { getInfo(testParams) } doReturn testSunriseSunsetInfo
        }
        val ssoSkylight = SsoSkylight(mockClient)

        assertEquals(
            testSunriseSunsetInfo.toSkylightDay(testToday),
            ssoSkylight.getSkylightDay(testCoordinates, testToday)
        )
    }
}
