package drewhamilton.skylight.sso.conversions

import drewhamilton.skylight.models.*
import drewhamilton.skylight.sso.dates.SsoDateTimeFormat
import drewhamilton.skylight.sso.network.models.SunriseSunsetInfo

internal object ConversionConstants {
  val DATE_TIME_NONE = SsoDateTimeFormat().parse("1970-01-01T00:00:00+00:00")
  val DATE_TIME_ALWAYS_DAY = SsoDateTimeFormat().parse("1970-01-01T00:00:01+00:00")
}

internal fun SunriseSunsetInfo.toSkylightInfo(): SkylightInfo = when {
  civil_twilight_begin == ConversionConstants.DATE_TIME_NONE && sunrise == ConversionConstants.DATE_TIME_NONE ->
    NeverLight()
  civil_twilight_begin == ConversionConstants.DATE_TIME_ALWAYS_DAY &&
      sunrise == ConversionConstants.DATE_TIME_ALWAYS_DAY ->
    AlwaysDaytime()
  civil_twilight_begin == ConversionConstants.DATE_TIME_NONE -> AlwaysLight(sunrise, sunset)
  sunrise == ConversionConstants.DATE_TIME_NONE -> NeverDaytime(civil_twilight_begin, civil_twilight_end)
  else -> Typical(civil_twilight_begin, sunrise, sunset, civil_twilight_end)
}
