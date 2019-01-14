package drewhamilton.skylight.sso.network.models

import drewhamilton.skylight.sso.serialization.SsoDateTime
import java.util.*

data class SunriseSunsetInfo(
    @SsoDateTime val sunrise: Date,
    @SsoDateTime val sunset: Date,
    @SsoDateTime val civil_twilight_begin: Date,
    @SsoDateTime val civil_twilight_end: Date
)
