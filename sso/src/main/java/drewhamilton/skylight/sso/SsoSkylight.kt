package drewhamilton.skylight.sso

import drewhamilton.skylight.Coordinates
import drewhamilton.skylight.Skylight
import drewhamilton.skylight.SkylightDay
import drewhamilton.skylight.sso.network.ApiConstants
import drewhamilton.skylight.sso.network.InfoClient
import drewhamilton.skylight.sso.network.models.Params
import drewhamilton.skylight.sso.network.models.SunriseSunsetInfo
import java.util.Date
import javax.inject.Inject

/**
 * An implementation of [Skylight] that uses sunrise-sunset.org to determine [SkylightDay] for the given
 * coordinates.
 */
class SsoSkylight @Inject constructor(private val client: InfoClient) : Skylight {

    override fun getSkylightDay(coordinates: Coordinates, date: Date): SkylightDay {
        val params = Params(coordinates.latitude, coordinates.longitude, date)
        return client.getInfo(params).toSkylightDay()
    }
}

internal fun SunriseSunsetInfo.toSkylightDay(): SkylightDay = when {
    civil_twilight_begin == ApiConstants.DATE_TIME_NONE && sunrise == ApiConstants.DATE_TIME_NONE -> SkylightDay.NeverLight
    civil_twilight_begin == ApiConstants.DATE_TIME_ALWAYS_DAY && sunrise == ApiConstants.DATE_TIME_ALWAYS_DAY ->
        SkylightDay.AlwaysDaytime
    civil_twilight_begin == ApiConstants.DATE_TIME_NONE -> SkylightDay.AlwaysLight(sunrise, sunset)
    sunrise == ApiConstants.DATE_TIME_NONE -> SkylightDay.NeverDaytime(civil_twilight_begin, civil_twilight_end)
    else -> SkylightDay.Typical(civil_twilight_begin, sunrise, sunset, civil_twilight_end)
}
