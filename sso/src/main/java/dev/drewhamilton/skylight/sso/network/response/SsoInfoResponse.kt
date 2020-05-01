package dev.drewhamilton.skylight.sso.network.response

data class SsoInfoResponse(
    val results: SunriseSunsetInfo,
    val status: String
)
