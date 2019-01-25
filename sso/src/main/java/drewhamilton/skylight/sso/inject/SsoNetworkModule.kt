package drewhamilton.skylight.sso.inject

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import drewhamilton.skylight.sso.network.ApiConstants
import drewhamilton.skylight.sso.network.SsoApi
import drewhamilton.skylight.sso.serialization.SsoDateTimeAdapter
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
internal class SsoNetworkModule(private val httpLoggingInterceptor: HttpLoggingInterceptor? = null) {

    @Provides
    internal fun ssoApi(@Sso ssoRetrofit: Retrofit): SsoApi = ssoRetrofit.create(SsoApi::class.java)

    @Provides
    @Sso
    internal fun retrofit(@Sso moshi: Moshi, @Sso okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(ApiConstants.BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .build()

    @Provides
    @Sso
    internal fun moshi(dateTimeAdapter: SsoDateTimeAdapter) = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(dateTimeAdapter)
        .build()

    @Provides
    @Sso
    internal fun okHttpClient() =
        OkHttpClient().newBuilder().apply {
            httpLoggingInterceptor?.let {
                addInterceptor(httpLoggingInterceptor)
            }
        }.build()
}
