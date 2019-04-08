package drewhamilton.skylight.sso

import drewhamilton.skylight.Coordinates
import drewhamilton.skylight.Skylight
import drewhamilton.skylight.SkylightInfo
import drewhamilton.skylight.sso.network.ApiConstants
import drewhamilton.skylight.sso.network.InfoClient
import drewhamilton.skylight.sso.network.models.Params
import drewhamilton.skylight.sso.network.models.SunriseSunsetInfo
import java.util.Date
import javax.inject.Inject

/**
 * An implementation of [Skylight] that uses sunrise-sunset.org to determine [SkylightInfo] for the given
 * coordinates.
 */
class SsoSkylight @Inject constructor(private val client: InfoClient) : Skylight {

    override fun determineSkylightInfo(coordinates: Coordinates, date: Date): SkylightInfo {
        val params = Params(coordinates.latitude, coordinates.longitude, date)
        return client.getInfo(params).toSkylightInfo()
    }
}

internal fun SunriseSunsetInfo.toSkylightInfo(): SkylightInfo = when {
    civil_twilight_begin == ApiConstants.DATE_TIME_NONE && sunrise == ApiConstants.DATE_TIME_NONE -> SkylightInfo.NeverLight
    civil_twilight_begin == ApiConstants.DATE_TIME_ALWAYS_DAY && sunrise == ApiConstants.DATE_TIME_ALWAYS_DAY ->
        SkylightInfo.AlwaysDaytime
    civil_twilight_begin == ApiConstants.DATE_TIME_NONE -> SkylightInfo.AlwaysLight(sunrise, sunset)
    sunrise == ApiConstants.DATE_TIME_NONE -> SkylightInfo.NeverDaytime(civil_twilight_begin, civil_twilight_end)
    else -> SkylightInfo.Typical(civil_twilight_begin, sunrise, sunset, civil_twilight_end)
}
