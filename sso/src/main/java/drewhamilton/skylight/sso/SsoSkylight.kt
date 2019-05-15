package drewhamilton.skylight.sso

import drewhamilton.skylight.Coordinates
import drewhamilton.skylight.NewSkylightDay
import drewhamilton.skylight.Skylight
import drewhamilton.skylight.SkylightDay
import drewhamilton.skylight.sso.network.ApiConstants
import drewhamilton.skylight.sso.network.InfoClient
import drewhamilton.skylight.sso.network.request.NewParams
import drewhamilton.skylight.sso.network.request.Params
import drewhamilton.skylight.sso.network.response.NewSunriseSunsetInfo
import drewhamilton.skylight.sso.network.response.SunriseSunsetInfo
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.Date
import javax.inject.Inject

/**
 * An implementation of [Skylight] that uses sunrise-sunset.org to determine [SkylightDay] for the given
 * coordinates.
 */
class SsoSkylight @Inject constructor(private val client: InfoClient) : Skylight {

    override fun getSkylightDay(coordinates: Coordinates, date: LocalDate): NewSkylightDay {
        val params = NewParams(coordinates.latitude, coordinates.longitude, date)
        return client.getInfo(params).toSkylightDay(date)
    }

    override fun getSkylightDay(coordinates: Coordinates, date: Date): SkylightDay {
        val params = Params(coordinates.latitude, coordinates.longitude, date)
        return client.getInfo(params).toSkylightDay()
    }
}

internal fun NewSunriseSunsetInfo.toSkylightDay(date: LocalDate): NewSkylightDay {
    return when {
        civil_twilight_begin == ApiConstants.NEW_DATE_TIME_NONE && sunrise == ApiConstants.NEW_DATE_TIME_NONE ->
            NewSkylightDay.NeverLight(date)
        civil_twilight_begin == ApiConstants.NEW_DATE_TIME_ALWAYS_DAY && sunrise == ApiConstants.NEW_DATE_TIME_ALWAYS_DAY ->
            NewSkylightDay.AlwaysDaytime(date)
        civil_twilight_begin == ApiConstants.NEW_DATE_TIME_NONE ->
            NewSkylightDay.AlwaysLight(date, sunrise.toOffsetTime(), sunset.toOffsetTime())
        sunrise == ApiConstants.NEW_DATE_TIME_NONE ->
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

private fun ZonedDateTime.toOffsetTime() = toOffsetDateTime().toOffsetTime()

internal fun SunriseSunsetInfo.toSkylightDay(): SkylightDay = when {
    civil_twilight_begin == ApiConstants.DATE_TIME_NONE && sunrise == ApiConstants.DATE_TIME_NONE -> SkylightDay.NeverLight
    civil_twilight_begin == ApiConstants.DATE_TIME_ALWAYS_DAY && sunrise == ApiConstants.DATE_TIME_ALWAYS_DAY ->
        SkylightDay.AlwaysDaytime
    civil_twilight_begin == ApiConstants.DATE_TIME_NONE -> SkylightDay.AlwaysLight(sunrise, sunset)
    sunrise == ApiConstants.DATE_TIME_NONE -> SkylightDay.NeverDaytime(civil_twilight_begin, civil_twilight_end)
    else -> SkylightDay.Typical(civil_twilight_begin, sunrise, sunset, civil_twilight_end)
}
