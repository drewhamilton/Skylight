package dev.drewhamilton.skylight.sunrise_sunset_org.dagger

import dev.drewhamilton.skylight.Coordinates
import java.time.LocalDate
import java.time.Month
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class SunriseSunsetOrgSkylightDaggerTest {

    private var mockWebServer: MockWebServer? = null

    @After
    fun tearDown() {
        mockWebServer?.shutdown()
    }

    @Test fun `Dagger-provided SunriseSunsetOrgSkylight instance uses given OkHttp instance`() {
        mockWebServer = MockWebServer()
        mockWebServer!!.start()

        val testCoordinates = Coordinates(12.3, 45.6)
        val testDate = LocalDate.of(1970, Month.JANUARY, 26)

        val fakeNetworkInterceptor = FakeInterceptor()
        val testOkHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(fakeNetworkInterceptor)
            .build()

        val skylight = SunriseSunsetOrgSkylightComponent.create(testOkHttpClient).skylight
        skylight.getSkylightDay(testCoordinates, testDate)

        val expectedUrl = "https://api.sunrise-sunset.org/json?" +
                "lat=${testCoordinates.latitude}&" +
                "lng=${testCoordinates.longitude}&" +
                "date=1970-01-26&formatted=0"
        assertEquals(1, fakeNetworkInterceptor.interceptedRequests)
        assertNotNull(fakeNetworkInterceptor.interceptedRequest)
        assertEquals(expectedUrl, fakeNetworkInterceptor.interceptedRequest?.url().toString())
    }

    private class FakeInterceptor : Interceptor {
        var interceptedRequests = 0
        var interceptedRequest: Request? = null

        override fun intercept(chain: Interceptor.Chain): Response {
            val request: Request = chain.request()
            interceptedRequest = request
            ++interceptedRequests

            return chain.proceed(request)
        }
    }
}
