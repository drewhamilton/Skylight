package drewhamilton.skylight.sso.network.response

import drewhamilton.skylight.sso.network.SsoDateTime
import java.time.ZonedDateTime

data class SunriseSunsetInfo(
    @SsoDateTime val sunrise: ZonedDateTime,
    @SsoDateTime val sunset: ZonedDateTime,
    @SsoDateTime val civil_twilight_begin: ZonedDateTime,
    @SsoDateTime val civil_twilight_end: ZonedDateTime
)
