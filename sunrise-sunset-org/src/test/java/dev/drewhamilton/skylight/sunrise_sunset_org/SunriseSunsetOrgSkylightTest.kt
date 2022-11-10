package dev.drewhamilton.skylight.sunrise_sunset_org

import dev.drewhamilton.skylight.Coordinates
import dev.drewhamilton.skylight.sunrise_sunset_org.network.request.Params
import dev.drewhamilton.skylight.sunrise_sunset_org.network.response.SunriseSunsetInfo
import dev.drewhamilton.skylight.sunrise_sunset_org.network.response.SunriseSunsetOrgInfoResponse
import dev.drewhamilton.skylight.sunrise_sunset_org.test.Dispatcher
import dev.drewhamilton.skylight.sunrise_sunset_org.test.TestSunriseSunsetOrgApi
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class SunriseSunsetOrgSkylightTest {

    private val moshi = SunriseSunsetOrgSkylight.instantiateMoshi()

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

    private val successResponse: Response<SunriseSunsetOrgInfoResponse>
        get() = Response.success(SunriseSunsetOrgInfoResponse(testSunriseSunsetInfo, "Dummy status"))

    @Test
    fun `getInfo emits API result`() = runTest {
        val api = TestSunriseSunsetOrgApi(testParams to successResponse)
        val sunriseSunsetOrgSkylight = SunriseSunsetOrgSkylight(api)

        assertEquals(
            testSunriseSunsetInfo.toSkylightDay(testDate),
            sunriseSunsetOrgSkylight.getSkylightDay(testCoordinates, testDate)
        )
    }

    @Test(expected = HttpException::class)
    fun `getInfo throws HttpException when API result is an error`() = runTest {
        val api = TestSunriseSunsetOrgApi(
            testParams to Response.error(401, ResponseBody.create(null, "Content"))
        )
        val sunriseSunsetOrgSkylight = SunriseSunsetOrgSkylight(api)

        sunriseSunsetOrgSkylight.getSkylightDay(testCoordinates, testDate)
    }

    /**
     * Ensures the internal call to [retrofit2.Retrofit.create] doesn't fail with the real Retrofit instance.
     */
    @Test fun `okhttp constructor succeeds`() {
        SunriseSunsetOrgSkylight(OkHttpClient())
    }

    @Test fun `retrofit with mock web server succeeds`() = runTest {
        val server = MockWebServer().apply {
            dispatcher = Dispatcher { request ->
                val path = request.path
                when (path.substring(0, path.indexOf("?"))) {
                    "/json" -> successResponse.toMockResponse()
                    else -> MockResponse().setResponseCode(404)
                }
            }
            start()
        }
        val baseUrl = server.url("/")
        val retrofit = SunriseSunsetOrgSkylight.instantiateRetrofit(OkHttpClient(), baseUrl)
        val skylight = SunriseSunsetOrgSkylight(retrofit)

        val result = skylight.getSkylightDay(testCoordinates, testDate)
        assertEquals(testSunriseSunsetInfo.toSkylightDay(testDate), result)
    }

    private fun Response<SunriseSunsetOrgInfoResponse>.toMockResponse(): MockResponse = MockResponse()
        .setResponseCode(code())
        .setBody(moshi.adapter(SunriseSunsetOrgInfoResponse::class.java).toJson(body()))
}
