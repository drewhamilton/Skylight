package drewhamilton.skylight.backport.sso

import drewhamilton.skylight.backport.Coordinates
import drewhamilton.skylight.backport.Skylight
import drewhamilton.skylight.backport.SkylightDay
import drewhamilton.skylight.backport.sso.network.toSsoDateString
import drewhamilton.skylight.backport.sso.network.toZonedDateTime
import drewhamilton.skylight.sso.network.ApiConstants
import drewhamilton.skylight.sso.network.SsoApi
import drewhamilton.skylight.sso.network.request.Params
import drewhamilton.skylight.sso.network.response.SunriseSunsetInfo
import org.threeten.bp.LocalDate
import retrofit2.HttpException
import javax.inject.Inject

/**
 * An implementation of [Skylight] that uses sunrise-sunset.org to determine a [SkylightDay] for the given location and
 * date.
 */
class SsoSkylight @Inject constructor(
    private val api: SsoApi
) : Skylight {

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
                sunrise.toZonedDateTime(),
                sunset.toZonedDateTime()
            )
        sunrise == ApiConstants.DATE_TIME_NONE ->
            SkylightDay.NeverDaytime(
                civil_twilight_begin.toZonedDateTime(),
                civil_twilight_end.toZonedDateTime()
            )
        else ->
            SkylightDay.Typical(
                civil_twilight_begin.toZonedDateTime(),
                sunrise.toZonedDateTime(),
                sunset.toZonedDateTime(),
                civil_twilight_end.toZonedDateTime()
            )
    }
}
