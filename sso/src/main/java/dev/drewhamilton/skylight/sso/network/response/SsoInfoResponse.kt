package dev.drewhamilton.skylight.sso.network.response

internal data class SsoInfoResponse(
    val results: SunriseSunsetInfo,
    val status: String
)
