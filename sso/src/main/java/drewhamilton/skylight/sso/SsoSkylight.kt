package drewhamilton.skylight.sso

import drewhamilton.skylight.Coordinates
import drewhamilton.skylight.Skylight
import drewhamilton.skylight.SkylightDay
import drewhamilton.skylight.sso.network.ApiConstants
import drewhamilton.skylight.sso.network.SsoApi
import drewhamilton.skylight.sso.network.request.Params
import drewhamilton.skylight.sso.network.response.SunriseSunsetInfo
import drewhamilton.skylight.sso.network.toSsoDateString
import drewhamilton.skylight.sso.network.toZonedDateTime
import retrofit2.HttpException
import java.time.LocalDate
import javax.inject.Inject

/**
 * An implementation of [Skylight] that uses sunrise-sunset.org to determine [SkylightDay] for the given
 * coordinates.
 */
@Suppress("NewApi")
class SsoSkylight @Inject constructor(private val api: SsoApi) : Skylight {

    override fun getSkylightDay(coordinates: Coordinates, date: LocalDate): SkylightDay {
        val params = Params(coordinates.latitude, coordinates.longitude, date.toSsoDateString())
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
}

// TODO: Make this private and test it via the public method
internal fun SunriseSunsetInfo.toSkylightDay(date: LocalDate): SkylightDay {
    return when {
        civil_twilight_begin == ApiConstants.DATE_TIME_NONE && sunrise == ApiConstants.DATE_TIME_NONE ->
            SkylightDay.NeverLight(date)
        civil_twilight_begin == ApiConstants.DATE_TIME_ALWAYS_DAY && sunrise == ApiConstants.DATE_TIME_ALWAYS_DAY ->
            SkylightDay.AlwaysDaytime(date)
        civil_twilight_begin == ApiConstants.DATE_TIME_NONE ->
            SkylightDay.AlwaysLight(
                date,
                sunrise.asDateTimeToOffsetTime(),
                sunset.asDateTimeToOffsetTime()
            )
        sunrise == ApiConstants.DATE_TIME_NONE ->
            SkylightDay.NeverDaytime(
                date,
                civil_twilight_begin.asDateTimeToOffsetTime(),
                civil_twilight_end.asDateTimeToOffsetTime()
            )
        else ->
            SkylightDay.Typical(
                date,
                civil_twilight_begin.asDateTimeToOffsetTime(),
                sunrise.asDateTimeToOffsetTime(),
                sunset.asDateTimeToOffsetTime(),
                civil_twilight_end.asDateTimeToOffsetTime()
            )
    }
}

@Suppress("NewApi")
private fun String.asDateTimeToOffsetTime() = toZonedDateTime()
    .toOffsetDateTime()
    .toOffsetTime()
