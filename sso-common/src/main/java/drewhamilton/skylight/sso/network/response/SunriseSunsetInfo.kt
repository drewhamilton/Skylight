package drewhamilton.skylight.sso.network.response

import drewhamilton.skylight.sso.network.SsoDateTime

data class SunriseSunsetInfo(
    @SsoDateTime val sunrise: String,
    @SsoDateTime val sunset: String,
    @SsoDateTime val civil_twilight_begin: String,
    @SsoDateTime val civil_twilight_end: String
)
