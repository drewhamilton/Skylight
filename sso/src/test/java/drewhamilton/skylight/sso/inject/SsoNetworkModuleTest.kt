package drewhamilton.skylight.sso.inject

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.squareup.moshi.Moshi
import drewhamilton.skylight.sso.network.ApiConstants
import drewhamilton.skylight.sso.network.SsoApi
import drewhamilton.skylight.sso.serialization.SsoDate
import drewhamilton.skylight.sso.serialization.SsoDateTime
import drewhamilton.skylight.sso.serialization.SsoDateTimeAdapter
import io.reactivex.Scheduler
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

class SsoNetworkModuleTest {

    private val rxJava2CallAdapterFactorySchedulerFieldName = "scheduler"
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
    fun `retrofit builds retrofit instance with expected base URL, Moshi, OkHttp, and network Scheduler`() {
        val mockScheduler = mock<Scheduler>()
        val mockMoshi = mock<Moshi>()
        val mockOkHttpClient = mock<OkHttpClient>()

        val retrofit = SsoNetworkModule.retrofit(mockMoshi, mockOkHttpClient, mockScheduler)

        assertEquals(HttpUrl.parse(ApiConstants.BASE_URL), retrofit.baseUrl())

        val callAdapterFactories = retrofit.callAdapterFactories()
        // Retrofit includes a DefaultCallAdapterFactory so size is 1 more than what we set:
        assertEquals(2, callAdapterFactories.size)
        val callAdapterFactory = callAdapterFactories[0]
        assertTrue(callAdapterFactory is RxJava2CallAdapterFactory)
        // Scheduler member is not publicly available so use reflection to verify it:
        val schedulerField =
            RxJava2CallAdapterFactory::class.java.getDeclaredField(rxJava2CallAdapterFactorySchedulerFieldName)
        schedulerField.isAccessible = true
        assertSame(mockScheduler, schedulerField.get(callAdapterFactory))

        val converterFactories = retrofit.converterFactories()
        // Retrofit includes BuiltInConverters so size is 1 more than what we set:
        assertEquals(2, callAdapterFactories.size)
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
