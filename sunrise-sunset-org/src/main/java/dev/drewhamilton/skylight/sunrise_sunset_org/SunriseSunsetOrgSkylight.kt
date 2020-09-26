package dev.drewhamilton.skylight.sunrise_sunset_org

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dev.drewhamilton.skylight.Coordinates
import dev.drewhamilton.skylight.Skylight
import dev.drewhamilton.skylight.SkylightDay
import dev.drewhamilton.skylight.sunrise_sunset_org.network.ApiConstants
import dev.drewhamilton.skylight.sunrise_sunset_org.network.SunriseSunsetOrgApi
import dev.drewhamilton.skylight.sunrise_sunset_org.network.request.Params
import dev.drewhamilton.skylight.sunrise_sunset_org.network.response.SunriseSunsetInfo
import dev.drewhamilton.skylight.sunrise_sunset_org.network.toSunriseSunsetOrgDateString
import dev.drewhamilton.skylight.sunrise_sunset_org.network.toZonedDateTime
import java.time.LocalDate
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * An implementation of [Skylight] that uses sunrise-sunset.org to determine a [SkylightDay] for the given location
 * and date.
 */
class SunriseSunsetOrgSkylight internal constructor(
    private val api: SunriseSunsetOrgApi
) : Skylight {

    constructor(
        okHttpClient: OkHttpClient
    ) : this(instantiateRetrofit(okHttpClient).create(SunriseSunsetOrgApi::class.java))

    override fun getSkylightDay(coordinates: Coordinates, date: LocalDate): SkylightDay {
        val params = Params(coordinates.latitude, coordinates.longitude, date.toSunriseSunsetOrgDateString())
        return getInfoResults(params).toSkylightDay(date)
    }

    private fun getInfoResults(params: Params): SunriseSunsetInfo {
        val call = api.getInfo(params.lat, params.lng, params.date)

        val response = call.execute()
        val responseBody = response.body()
        if (response.isSuccessful && responseBody != null)
            return responseBody.results
        else
            throw HttpException(response)
    }

    private companion object {
        private fun instantiateRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(instantiateMoshi()))
            .client(okHttpClient)
            .build()

        private fun instantiateMoshi() = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
}

// TODO: Make this private and test it via the public method
internal fun SunriseSunsetInfo.toSkylightDay(date: LocalDate): SkylightDay {
    return when {
        civil_twilight_begin == ApiConstants.DATE_TIME_NONE && sunrise == ApiConstants.DATE_TIME_NONE ->
            SkylightDay.NeverLight(date = date)
        civil_twilight_begin == ApiConstants.DATE_TIME_ALWAYS_DAY && sunrise == ApiConstants.DATE_TIME_ALWAYS_DAY ->
            SkylightDay.AlwaysDaytime(date = date)
        else -> SkylightDay.Typical(
            date = date,
            dawn = civil_twilight_begin.toZonedDateTime()?.toInstant(),
            sunrise = sunrise.toZonedDateTime()?.toInstant(),
            sunset = sunset.toZonedDateTime()?.toInstant(),
            dusk = civil_twilight_end.toZonedDateTime()?.toInstant()
        )
    }
}
