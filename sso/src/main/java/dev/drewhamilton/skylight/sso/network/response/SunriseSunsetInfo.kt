package dev.drewhamilton.skylight.sso.network.response

import dev.drewhamilton.skylight.sso.network.SsoDateTime

// TODO: Convert to DSL thing
data class SunriseSunsetInfo(
    @SsoDateTime val sunrise: String,
    @SsoDateTime val sunset: String,
    @SsoDateTime val civil_twilight_begin: String,
    @SsoDateTime val civil_twilight_end: String
)
