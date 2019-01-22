package drewhamilton.skylight.sso

import drewhamilton.skylight.SkylightRepository
import drewhamilton.skylight.models.*
import drewhamilton.skylight.sso.network.ApiConstants
import drewhamilton.skylight.sso.network.InfoClient
import drewhamilton.skylight.sso.network.models.Params
import drewhamilton.skylight.sso.network.models.SunriseSunsetInfo
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class SsoSkylightRepository @Inject constructor(private val client: InfoClient) : SkylightRepository {

    override fun getSkylightInfo(coordinates: Coordinates, date: Date): Single<SkylightInfo> {
        val params = Params(coordinates.latitude, coordinates.longitude, date)
        return client.getInfo(params)
            .map { it.toSkylightInfo() }
    }
}

internal fun SunriseSunsetInfo.toSkylightInfo(): SkylightInfo = when {
    civil_twilight_begin == ApiConstants.DATE_TIME_NONE && sunrise == ApiConstants.DATE_TIME_NONE -> NeverLight()
    civil_twilight_begin == ApiConstants.DATE_TIME_ALWAYS_DAY && sunrise == ApiConstants.DATE_TIME_ALWAYS_DAY ->
        AlwaysDaytime()
    civil_twilight_begin == ApiConstants.DATE_TIME_NONE -> AlwaysLight(sunrise, sunset)
    sunrise == ApiConstants.DATE_TIME_NONE -> NeverDaytime(civil_twilight_begin, civil_twilight_end)
    else -> Typical(civil_twilight_begin, sunrise, sunset, civil_twilight_end)
}
