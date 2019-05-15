package drewhamilton.skylight.sso.network

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import drewhamilton.skylight.sso.network.request.Params
import drewhamilton.skylight.sso.network.response.NewSunriseSunsetInfo
import drewhamilton.skylight.sso.network.response.Response
import drewhamilton.skylight.sso.network.response.SunriseSunsetInfo
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
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

    private val dummyNewSunriseSunsetInfo = NewSunriseSunsetInfo(
        dummySunrise.toZonedDateTime(ZoneOffset.UTC),
        dummySunset.toZonedDateTime(ZoneOffset.UTC),
        dummyCivilTwilightBegin.toZonedDateTime(ZoneOffset.UTC),
        dummyCivilTwilightEnd.toZonedDateTime(ZoneOffset.UTC)
    )

    private lateinit var mockApi: SsoApi
    private lateinit var mockDateTimeAdapter: SsoDateTimeAdapter

    @Test
    fun `getInfo emits API result`() {
        mockDateTimeAdapter = mock {
            on { dateToString(dummyParams.date.toLocalDate(ZoneOffset.UTC)) } doReturn dummyDateString
            on { dateToString(dummyParams.date) } doReturn dummyDateString
            on { newDateFromString(dummyDateString) } doReturn dummyParams.date.toLocalDate(ZoneOffset.UTC)
        }
        mockApi = mock {
            on {
                getInfo(dummyParams.lat, dummyParams.lng, dummyDateString, 0)
            } doReturn DummyCall.success(
                Response(
                    dummyNewSunriseSunsetInfo,
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
            on { dateToString(dummyParams.date.toLocalDate(ZoneOffset.UTC)) } doReturn dummyDateString
            on { dateToString(dummyParams.date) } doReturn dummyDateString
            on { newDateFromString(dummyDateString) } doReturn dummyParams.date.toLocalDate(ZoneOffset.UTC)
        }
        mockApi = mock {
            on {
                getInfo(dummyParams.lat, dummyParams.lng, dummyDateString, 0)
            } doReturn DummyCall.error(401, ResponseBody.create(null, "Content"))
        }

        val infoClient = InfoClient(mockApi, mockDateTimeAdapter)
        infoClient.getInfo(dummyParams)
    }

    private fun Date.toZonedDateTime(zoneId: ZoneId) = ZonedDateTime.ofInstant(Instant.ofEpochMilli(time), zoneId)

    private fun Date.toLocalDate(zoneId: ZoneId) =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(time), zoneId).toLocalDate()
}
