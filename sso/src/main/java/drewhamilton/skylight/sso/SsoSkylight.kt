package drewhamilton.skylight.sso

import drewhamilton.skylight.Coordinates
import drewhamilton.skylight.NewSkylightDay
import drewhamilton.skylight.Skylight
import drewhamilton.skylight.sso.network.ApiConstants
import drewhamilton.skylight.sso.network.InfoClient
import drewhamilton.skylight.sso.network.request.Params
import drewhamilton.skylight.sso.network.response.SunriseSunsetInfo
import java.time.LocalDate
import java.time.ZonedDateTime
import javax.inject.Inject

/**
 * An implementation of [Skylight] that uses sunrise-sunset.org to determine [NewSkylightDay] for the given
 * coordinates.
 */
class SsoSkylight @Inject constructor(private val client: InfoClient) : Skylight {

    override fun getSkylightDay(coordinates: Coordinates, date: LocalDate): NewSkylightDay {
        val params = Params(coordinates.latitude, coordinates.longitude, date)
        return client.getInfo(params).toSkylightDay(date)
    }
}

internal fun SunriseSunsetInfo.toSkylightDay(date: LocalDate): NewSkylightDay {
    return when {
        civil_twilight_begin == ApiConstants.DATE_TIME_NONE && sunrise == ApiConstants.DATE_TIME_NONE ->
            NewSkylightDay.NeverLight(date)
        civil_twilight_begin == ApiConstants.DATE_TIME_ALWAYS_DAY && sunrise == ApiConstants.DATE_TIME_ALWAYS_DAY ->
            NewSkylightDay.AlwaysDaytime(date)
        civil_twilight_begin == ApiConstants.DATE_TIME_NONE ->
            NewSkylightDay.AlwaysLight(date, sunrise.toOffsetTime(), sunset.toOffsetTime())
        sunrise == ApiConstants.DATE_TIME_NONE ->
            NewSkylightDay.NeverDaytime(date, civil_twilight_begin.toOffsetTime(), civil_twilight_end.toOffsetTime())
        else -> NewSkylightDay.Typical(
            date,
            civil_twilight_begin.toOffsetTime(),
            sunrise.toOffsetTime(),
            sunset.toOffsetTime(),
            civil_twilight_end.toOffsetTime()
        )
    }
}

internal fun ZonedDateTime.toOffsetTime() = toOffsetDateTime().toOffsetTime()
