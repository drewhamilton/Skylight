package dev.drewhamilton.skylight.sunrise_sunset_org.network

import okhttp3.HttpUrl.Companion.toHttpUrl

internal object ApiConstants {

    internal val BASE_URL = "https://api.sunrise-sunset.org/".toHttpUrl()

    internal const val DATE_TIME_NONE = "1970-01-01T00:00:00+00:00"
    internal const val DATE_TIME_ALWAYS_DAY = "1970-01-01T00:00:01+00:00"
}
