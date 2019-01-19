package drewhamilton.skylight.sso.inject

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import drewhamilton.skylight.RxSkylightRepository
import drewhamilton.skylight.sso.BASE_URL_SUNRISE_SUNSET_ORG
import drewhamilton.skylight.sso.SsoSkylightRepository
import drewhamilton.skylight.sso.network.InfoClient
import drewhamilton.skylight.sso.network.SsoApi
import drewhamilton.skylight.sso.serialization.SsoDateTimeAdapter
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class SsoSkylightModule(private val httpLoggingInterceptor: HttpLoggingInterceptor? = null) {

  @Provides
  @Sso
  fun skylightRepository(client: InfoClient): RxSkylightRepository = SsoSkylightRepository(client)

  @Provides
  @Singleton
  fun ssoApi(@Sso ssoRetrofit: Retrofit): SsoApi = ssoRetrofit.create(SsoApi::class.java)

  @Provides
  @Singleton
  @Sso
  internal fun retrofit(@Sso moshi: Moshi, @Sso okHttpClient: OkHttpClient) = Retrofit.Builder()
      .baseUrl(BASE_URL_SUNRISE_SUNSET_ORG)
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
  internal fun okHttpClient(): OkHttpClient {
    val builder = OkHttpClient().newBuilder()

    httpLoggingInterceptor?.let {
      builder.addInterceptor(httpLoggingInterceptor)
    }

    return builder.build()
  }
}
