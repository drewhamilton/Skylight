package drewhamilton.skylight.sso.dagger

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.squareup.moshi.Moshi
import drewhamilton.skylight.sso.network.ApiConstants
import drewhamilton.skylight.sso.network.SsoApi
import drewhamilton.skylight.sso.serialization.SsoDate
import drewhamilton.skylight.sso.serialization.SsoDateTime
import drewhamilton.skylight.sso.serialization.SsoDateTimeAdapter
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Date

class SsoNetworkModuleTest {

    private val moshiConverterFactoryMoshiFieldName = "moshi"

    @Test
    fun `ssoApi creates SsoApi with given Retrofit instance`() {
        val dummyApi = mock<SsoApi>()
        val mockRetrofit = mock<Retrofit> {
            on { create(SsoApi::class.java) } doReturn dummyApi
        }

        assertEquals(dummyApi, SsoNetworkModule.ssoApi(mockRetrofit))
    }

    @Test
    fun `retrofit builds retrofit instance with expected base URL, Moshi, and OkHttp`() {
        val mockMoshi = mock<Moshi>()
        val mockOkHttpClient = mock<OkHttpClient>()

        val retrofit = SsoNetworkModule.retrofit(mockMoshi, mockOkHttpClient)

        assertEquals(HttpUrl.parse(ApiConstants.BASE_URL), retrofit.baseUrl())

        val converterFactories = retrofit.converterFactories()
        // Retrofit includes BuiltInConverters so size is 1 more than what we set:
        assertTrue(converterFactories.size >= 2)
        val converterFactory = converterFactories[1]
        assertTrue(converterFactory is MoshiConverterFactory)
        // Moshi member is not publicly available so use reflection to verify it:
        val moshiField =
            MoshiConverterFactory::class.java.getDeclaredField(moshiConverterFactoryMoshiFieldName)
        moshiField.isAccessible = true
        assertSame(mockMoshi, moshiField.get(converterFactory))

        assertSame(mockOkHttpClient, retrofit.callFactory())
    }

    @Test
    fun `moshi includes SsoDateTimeAdapter`() {
        val ssoDateTimeAdapter = SsoDateTimeAdapter()

        val moshi = SsoNetworkModule.moshi(ssoDateTimeAdapter)

        assertNotNull(moshi.adapter<Date>(Date::class.java, SsoDate::class.java))
        assertNotNull(moshi.adapter<Date>(Date::class.java, SsoDateTime::class.java))
    }
}
