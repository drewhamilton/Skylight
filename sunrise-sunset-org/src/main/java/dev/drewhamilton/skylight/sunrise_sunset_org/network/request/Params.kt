package dev.drewhamilton.skylight.sunrise_sunset_org.network.request

import dev.drewhamilton.skylight.sunrise_sunset_org.network.SunriseSunsetOrgDate

internal data class Params(val lat: Double, val lng: Double, @SunriseSunsetOrgDate val date: String)
