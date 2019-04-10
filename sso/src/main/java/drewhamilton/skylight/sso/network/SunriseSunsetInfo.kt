package drewhamilton.skylight.sso.network

import java.util.Date

data class SunriseSunsetInfo(
    @SsoDateTime val sunrise: Date,
    @SsoDateTime val sunset: Date,
    @SsoDateTime val civil_twilight_begin: Date,
    @SsoDateTime val civil_twilight_end: Date
)
