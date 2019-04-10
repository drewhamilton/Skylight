package drewhamilton.skylight.sso.network

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class InfoClientTest {

    private val testDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    private val dummyParams = Params(76.5, 43.2, Date(754727L))
    private val dummyDateString = testDateFormat.format(dummyParams.date)

    private val dummyCivilTwilightBegin = Date(888_888_810_000L)
    private val dummySunrise = Date(888_888_820_000L)
    private val dummySunset = Date(888_888_830_000L)
    private val dummyCivilTwilightEnd = Date(888_888_840_000L)

    private val dummySunriseSunsetInfo = SunriseSunsetInfo(
        dummySunrise,
        dummySunset,
        dummyCivilTwilightBegin,
        dummyCivilTwilightEnd
    )

    private lateinit var mockApi: SsoApi
    private lateinit var mockDateTimeAdapter: SsoDateTimeAdapter

    @Test
    fun `getInfo emits API result`() {
        mockDateTimeAdapter = mock {
            on { dateToString(dummyParams.date) } doReturn dummyDateString
        }
        mockApi = mock {
            on {
                getInfo(dummyParams.lat, dummyParams.lng, dummyDateString, 0)
            } doReturn DummyCall.success(
                Response(
                    dummySunriseSunsetInfo,
                    "Dummy status"
                )
            )
        }

        val infoClient = InfoClient(mockApi, mockDateTimeAdapter)
        assertEquals(dummySunriseSunsetInfo, infoClient.getInfo(dummyParams))
    }

    @Test(expected = HttpException::class)
    fun `getInfo throws HttpException when API result is an error`() {
        mockDateTimeAdapter = mock {
            on { dateToString(dummyParams.date) } doReturn dummyDateString
        }
        mockApi = mock {
            on {
                getInfo(dummyParams.lat, dummyParams.lng, dummyDateString, 0)
            } doReturn DummyCall.error(401, ResponseBody.create(null, "Content"))
        }

        val infoClient = InfoClient(mockApi, mockDateTimeAdapter)
        infoClient.getInfo(dummyParams)
    }
}
