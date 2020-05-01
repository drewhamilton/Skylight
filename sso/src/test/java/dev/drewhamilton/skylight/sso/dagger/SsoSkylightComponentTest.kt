package dev.drewhamilton.skylight.sso.dagger

import dev.drewhamilton.skylight.Coordinates
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNotSame
import org.junit.Test
import java.time.LocalDate
import java.time.Month

class SsoSkylightComponentTest {

    private var mockWebServer: MockWebServer? = null

    @After
    fun tearDown() {
        mockWebServer?.shutdown()
    }

    @Test
    fun `create without OkHttpClient compiles and runs`() {
        val component = SsoSkylightComponent.create()
        assertNotSame(component.skylight(), component.skylight())
    }

    @Test
    fun `create with custom OkHttpClient sends that OkHttpClient to the Skylight instance`() {
        mockWebServer = MockWebServer()
        mockWebServer!!.start()

        val testCoordinates = Coordinates(12.3, 45.6)
        val testDate = LocalDate.of(1970, Month.JANUARY, 26)

        val fakeNetworkInterceptor = FakeInterceptor()
        val testOkHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(fakeNetworkInterceptor)
            .build()

        val component = SsoSkylightComponent.create(testOkHttpClient)
        val skylight = component.skylight()
        skylight.getSkylightDay(testCoordinates, testDate)

        val expectedUrl = "https://api.sunrise-sunset.org/json?lat=${testCoordinates.latitude}&lng=" +
                "${testCoordinates.longitude}&date=1970-01-26&formatted=0"
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
