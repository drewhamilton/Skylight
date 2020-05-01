package dev.drewhamilton.skylight.sso

import dev.drewhamilton.skylight.Coordinates
import dev.drewhamilton.skylight.Skylight
import dev.drewhamilton.skylight.SkylightDay
import dev.drewhamilton.skylight.sso.network.ApiConstants
import dev.drewhamilton.skylight.sso.network.SsoApi
import dev.drewhamilton.skylight.sso.network.request.Params
import dev.drewhamilton.skylight.sso.network.response.SunriseSunsetInfo
import dev.drewhamilton.skylight.sso.network.toSsoDateString
import dev.drewhamilton.skylight.sso.network.toZonedDateTime
import retrofit2.HttpException
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

/**
 * An implementation of [Skylight] that uses sunrise-sunset.org to determine a [SkylightDay] for the given location
 * and date.
 */
class SsoSkylight @Inject constructor(
    private val api: SsoApi
) : Skylight {

    override fun getSkylightDay(coordinates: Coordinates, date: LocalDate, zoneId: ZoneId): SkylightDay {
        val params = Params(coordinates.latitude, coordinates.longitude, date.toSsoDateString())
        return getInfoResults(params).toSkylightDay(date, zoneId)
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
internal fun SunriseSunsetInfo.toSkylightDay(date: LocalDate, zoneId: ZoneId): SkylightDay {
    return when {
        civil_twilight_begin == ApiConstants.DATE_TIME_NONE && sunrise == ApiConstants.DATE_TIME_NONE ->
            SkylightDay.NeverLight { this.date = date }
        civil_twilight_begin == ApiConstants.DATE_TIME_ALWAYS_DAY && sunrise == ApiConstants.DATE_TIME_ALWAYS_DAY ->
            SkylightDay.AlwaysDaytime { this.date = date }
        else -> SkylightDay.Typical {
            this.date = date
            this.dawn = civil_twilight_begin.toZonedDateTime()?.withZoneSameInstant(zoneId)
            this.sunrise = this@toSkylightDay.sunrise.toZonedDateTime()?.withZoneSameInstant(zoneId)
            this.sunset = this@toSkylightDay.sunset.toZonedDateTime()?.withZoneSameInstant(zoneId)
            this.dusk = civil_twilight_end.toZonedDateTime()?.withZoneSameInstant(zoneId)
        }
    }
}
