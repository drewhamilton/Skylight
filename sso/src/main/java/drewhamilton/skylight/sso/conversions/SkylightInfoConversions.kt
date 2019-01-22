package drewhamilton.skylight.sso.conversions

import drewhamilton.skylight.models.*
import drewhamilton.skylight.sso.network.ApiConstants
import drewhamilton.skylight.sso.network.models.SunriseSunsetInfo

internal fun SunriseSunsetInfo.toSkylightInfo(): SkylightInfo = when {
  civil_twilight_begin == ApiConstants.DATE_TIME_NONE && sunrise == ApiConstants.DATE_TIME_NONE -> NeverLight()
  civil_twilight_begin == ApiConstants.DATE_TIME_ALWAYS_DAY && sunrise == ApiConstants.DATE_TIME_ALWAYS_DAY -> AlwaysDaytime()
  civil_twilight_begin == ApiConstants.DATE_TIME_NONE -> AlwaysLight(sunrise, sunset)
  sunrise == ApiConstants.DATE_TIME_NONE -> NeverDaytime(civil_twilight_begin, civil_twilight_end)
  else -> Typical(civil_twilight_begin, sunrise, sunset, civil_twilight_end)
}
