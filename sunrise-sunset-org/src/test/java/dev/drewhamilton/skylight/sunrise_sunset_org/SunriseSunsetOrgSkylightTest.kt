package dev.drewhamilton.skylight.sunrise_sunset_org

import dev.drewhamilton.skylight.Coordinates
import dev.drewhamilton.skylight.sunrise_sunset_org.network.request.Params
import dev.drewhamilton.skylight.sunrise_sunset_org.network.response.SunriseSunsetInfo
import dev.drewhamilton.skylight.sunrise_sunset_org.network.response.SunriseSunsetOrgInfoResponse
import dev.drewhamilton.skylight.sunrise_sunset_org.test.TestSunriseSunsetOrgApi
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.mock.Calls

class SunriseSunsetOrgSkylightTest {

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

    @Test
    fun `getInfo emits API result`() {
        val api = TestSunriseSunsetOrgApi(
            testParams to Calls.response(SunriseSunsetOrgInfoResponse(testSunriseSunsetInfo, "Dummy status"))
        )
        val sunriseSunsetOrgSkylight = SunriseSunsetOrgSkylight(api)

        assertEquals(
            testSunriseSunsetInfo.toSkylightDay(testDate),
            sunriseSunsetOrgSkylight.getSkylightDay(testCoordinates, testDate)
        )
    }

    @Test(expected = HttpException::class)
    fun `getInfo throws HttpException when API result is an error`() {
        val api = TestSunriseSunsetOrgApi(
            testParams to Calls.response(Response.error(401, ResponseBody.create(null, "Content")))
        )
        val sunriseSunsetOrgSkylight = SunriseSunsetOrgSkylight(api)

        sunriseSunsetOrgSkylight.getSkylightDay(testCoordinates, testDate)
    }
}
