package drewhamilton.skylight.sso.network

import java.time.ZonedDateTime

internal object ApiConstants {

    internal const val BASE_URL = "https://api.sunrise-sunset.org/"

    internal val DATE_TIME_NONE = SsoDateTimeFormat().parse("1970-01-01T00:00:00+00:00")
    internal val DATE_TIME_ALWAYS_DAY = SsoDateTimeFormat().parse("1970-01-01T00:00:01+00:00")

    internal val NEW_DATE_TIME_NONE = ZonedDateTime.parse("1970-01-01T00:00:00+00:00", SsoDateTimeFormat.get())
    internal val NEW_DATE_TIME_ALWAYS_DAY = ZonedDateTime.parse("1970-01-01T00:00:01+00:00", SsoDateTimeFormat.get())
}
