package dev.drewhamilton.skylight.sunrise_sunset_org.network.response

import dev.drewhamilton.skylight.sunrise_sunset_org.network.SunriseSunsetOrgDateTime

internal data class SunriseSunsetInfo(
    @SunriseSunsetOrgDateTime val sunrise: String,
    @SunriseSunsetOrgDateTime val sunset: String,
    @SunriseSunsetOrgDateTime val civil_twilight_begin: String,
    @SunriseSunsetOrgDateTime val civil_twilight_end: String
)
