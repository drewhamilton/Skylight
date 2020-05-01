package dev.drewhamilton.skylight.sso

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import dev.drewhamilton.skylight.Coordinates
import dev.drewhamilton.skylight.sso.network.DummyCall
import dev.drewhamilton.skylight.sso.network.SsoApi
import dev.drewhamilton.skylight.sso.network.request.Params
import dev.drewhamilton.skylight.sso.network.response.SsoInfoResponse
import dev.drewhamilton.skylight.sso.network.response.SunriseSunsetInfo
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import java.time.LocalDate
import java.time.Month
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class SsoSkylightTest {

    private val testDawn = "1999-08-21T08:30:40+00:00"
    private val testSunrise = "1999-08-21T09:30:40+00:00"
    private val testSunset = "1999-08-21T20:30:40+00:00"
    private val testDusk = "1999-08-21T21:30:40+00:00"

    private val testLatitude = 12.3
    private val testLongitude = 45.6
    private val testDate = LocalDate.of(1999, Month.AUGUST, 21)
    private val testDateString = testDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
    private val testCoordinates = Coordinates(testLatitude, testLongitude)
    private val testParams = Params(testLatitude, testLongitude, testDateString)

    private val testSunriseSunsetInfo = SunriseSunsetInfo(testSunrise, testSunset, this.testDawn, this.testDusk)

    private lateinit var mockApi: SsoApi

    @Test
    fun `getInfo emits API result`() {
        mockApi = mock {
            on {
                getInfo(testParams.lat, testParams.lng, testDateString, 0)
            } doReturn DummyCall.success(SsoInfoResponse(testSunriseSunsetInfo, "Dummy status"))
        }

        val ssoSkylight = SsoSkylight(mockApi)
        assertEquals(
            testSunriseSunsetInfo.toSkylightDay(testDate, ZoneOffset.UTC),
            ssoSkylight.getSkylightDay(testCoordinates, testDate, ZoneOffset.UTC)
        )
    }

    @Test(expected = HttpException::class)
    fun `getInfo throws HttpException when API result is an error`() {
        mockApi = mock {
            on {
                getInfo(testParams.lat, testParams.lng, testDateString, 0)
            } doReturn DummyCall.error(401, ResponseBody.create(null, "Content"))
        }

        val ssoSkylight = SsoSkylight(mockApi)
        ssoSkylight.getSkylightDay(testCoordinates, testDate)
    }
}
