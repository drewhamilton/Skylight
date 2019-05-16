package drewhamilton.skylight.sso.network

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import drewhamilton.skylight.sso.network.request.Params
import drewhamilton.skylight.sso.network.response.SunriseSunsetInfo
import drewhamilton.skylight.sso.network.response.Response
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.ZonedDateTime

class InfoClientTest {

    private val testDateString = "2019-05-16"
    private val testParams = Params(76.5, 43.2, LocalDate.parse(testDateString))

    private val testCivilTwilightBegin = ZonedDateTime.ofInstant(Instant.ofEpochMilli(888_888_810_000L), ZoneOffset.UTC)
    private val testSunrise = ZonedDateTime.ofInstant(Instant.ofEpochMilli(888_888_820_000L), ZoneOffset.UTC)
    private val testSunset = ZonedDateTime.ofInstant(Instant.ofEpochMilli(888_888_830_000L), ZoneOffset.UTC)
    private val testCivilTwilightEnd = ZonedDateTime.ofInstant(Instant.ofEpochMilli(888_888_840_000L), ZoneOffset.UTC)

    private val testSunriseSunsetInfo = SunriseSunsetInfo(
        testSunrise,
        testSunset,
        testCivilTwilightBegin,
        testCivilTwilightEnd
    )

    private lateinit var mockApi: SsoApi
    private lateinit var mockDateTimeAdapter: SsoDateTimeAdapter

    @Test
    fun `getInfo emits API result`() {
        mockDateTimeAdapter = mock {
            on { dateToString(testParams.date) } doReturn testDateString
        }
        mockApi = mock {
            on {
                getInfo(testParams.lat, testParams.lng, testDateString, 0)
            } doReturn DummyCall.success(
                Response(testSunriseSunsetInfo, "Dummy status")
            )
        }

        val infoClient = InfoClient(mockApi, mockDateTimeAdapter)
        assertEquals(testSunriseSunsetInfo, infoClient.getInfo(testParams))
    }

    @Test(expected = HttpException::class)
    fun `getInfo throws HttpException when API result is an error`() {
        mockDateTimeAdapter = mock {
            on { dateToString(testParams.date) } doReturn testDateString
        }
        mockApi = mock {
            on {
                getInfo(testParams.lat, testParams.lng, testDateString, 0)
            } doReturn DummyCall.error(401, ResponseBody.create(null, "Content"))
        }

        val infoClient = InfoClient(mockApi, mockDateTimeAdapter)
        infoClient.getInfo(testParams)
    }
}
