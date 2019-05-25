package drewhamilton.skylight.backport.sso

import com.nhaarman.mockitokotlin2.mock
import drewhamilton.skylight.backport.Coordinates
import drewhamilton.skylight.backport.sso.network.DummyCall
import drewhamilton.skylight.sso.network.SsoApi
import drewhamilton.skylight.sso.network.request.Params
import drewhamilton.skylight.sso.network.response.Response
import drewhamilton.skylight.sso.network.response.SunriseSunsetInfo
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import org.threeten.bp.LocalDate
import org.threeten.bp.Month
import org.threeten.bp.format.DateTimeFormatter
import retrofit2.HttpException

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

    private val testSunriseSunsetInfo = SunriseSunsetInfo(
        testSunrise,
        testSunset,
        this.testDawn,
        this.testDusk
    )

    private lateinit var mockApi: SsoApi

    @Test
    fun `getInfo emits API result`() {
        mockApi = mock {
            on {
                getInfo(testParams.lat, testParams.lng, testDateString, 0)
            } doReturn DummyCall.success(Response(testSunriseSunsetInfo, "Dummy status"))
        }

        val ssoSkylight = SsoSkylightBackport(mockApi)
        assertEquals(
            testSunriseSunsetInfo.toSkylightDayBackport(testDate),
            ssoSkylight.getSkylightDay(testCoordinates, testDate)
        )
    }

    @Test(expected = HttpException::class)
    fun `getInfo throws HttpException when API result is an error`() {
        mockApi = mock {
            on {
                getInfo(testParams.lat, testParams.lng, testDateString, 0)
            } doReturn DummyCall.error(401, ResponseBody.create(null, "Content"))
        }

        val ssoSkylight = SsoSkylightBackport(mockApi)
        ssoSkylight.getSkylightDay(testCoordinates, testDate)
    }
}
