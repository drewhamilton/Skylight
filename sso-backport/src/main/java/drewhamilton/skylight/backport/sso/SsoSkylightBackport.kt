package drewhamilton.skylight.backport.sso

import drewhamilton.skylight.backport.CoordinatesBackport
import drewhamilton.skylight.backport.SkylightBackport
import drewhamilton.skylight.backport.SkylightDayBackport
import drewhamilton.skylight.backport.sso.network.toSsoDateString
import drewhamilton.skylight.backport.sso.network.toZonedDateTimeBackport
import drewhamilton.skylight.sso.network.ApiConstants
import drewhamilton.skylight.sso.network.SsoApi
import drewhamilton.skylight.sso.network.request.Params
import drewhamilton.skylight.sso.network.response.SunriseSunsetInfo
import org.threeten.bp.LocalDate
import retrofit2.HttpException
import javax.inject.Inject

/**
 * An implementation of [SkylightBackport] that uses sunrise-sunset.org to determine a [SkylightDayBackport] for the
 * given location and date.
 */
class SsoSkylightBackport @Inject constructor(
    private val api: SsoApi
) : SkylightBackport {

    override fun getSkylightDay(coordinates: CoordinatesBackport, date: LocalDate): SkylightDayBackport {
        val params = Params(coordinates.latitude, coordinates.longitude, date.toSsoDateString())
        return getInfoResults(params).toSkylightDayBackport(date)
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
internal fun SunriseSunsetInfo.toSkylightDayBackport(date: LocalDate): SkylightDayBackport {
    return when {
        civil_twilight_begin == ApiConstants.DATE_TIME_NONE && sunrise == ApiConstants.DATE_TIME_NONE ->
            SkylightDayBackport.NeverLight(date)
        civil_twilight_begin == ApiConstants.DATE_TIME_ALWAYS_DAY && sunrise == ApiConstants.DATE_TIME_ALWAYS_DAY ->
            SkylightDayBackport.AlwaysDaytime(date)
        civil_twilight_begin == ApiConstants.DATE_TIME_NONE ->
            SkylightDayBackport.AlwaysLight(
                date,
                sunrise.asDateTimeToOffsetTime(),
                sunset.asDateTimeToOffsetTime()
            )
        sunrise == ApiConstants.DATE_TIME_NONE ->
            SkylightDayBackport.NeverDaytime(
                date,
                civil_twilight_begin.asDateTimeToOffsetTime(),
                civil_twilight_end.asDateTimeToOffsetTime()
            )
        else ->
            SkylightDayBackport.Typical(
                date,
                civil_twilight_begin.asDateTimeToOffsetTime(),
                sunrise.asDateTimeToOffsetTime(),
                sunset.asDateTimeToOffsetTime(),
                civil_twilight_end.asDateTimeToOffsetTime()
            )
    }
}

private fun String.asDateTimeToOffsetTime() = toZonedDateTimeBackport()
    .toOffsetDateTime()
    .toOffsetTime()
