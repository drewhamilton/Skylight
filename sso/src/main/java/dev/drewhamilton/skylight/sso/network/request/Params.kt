package dev.drewhamilton.skylight.sso.network.request

import dev.drewhamilton.skylight.sso.network.SsoDate

data class Params(val lat: Double, val lng: Double, @SsoDate val date: String)
