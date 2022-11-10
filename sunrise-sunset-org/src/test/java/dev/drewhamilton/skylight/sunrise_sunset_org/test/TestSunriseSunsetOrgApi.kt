package dev.drewhamilton.skylight.sunrise_sunset_org.test

import dev.drewhamilton.skylight.sunrise_sunset_org.network.SunriseSunsetOrgApi
import dev.drewhamilton.skylight.sunrise_sunset_org.network.request.Params
import dev.drewhamilton.skylight.sunrise_sunset_org.network.response.SunriseSunsetOrgInfoResponse
import retrofit2.Call

internal class TestSunriseSunsetOrgApi(
    private val calls: Map<Params, Call<SunriseSunsetOrgInfoResponse>>,
) : SunriseSunsetOrgApi {

    constructor(vararg pairs: Pair<Params, Call<SunriseSunsetOrgInfoResponse>>) : this(mapOf(*pairs))

    override fun getInfo(lat: Double, lng: Double, date: String, formatted: Int): Call<SunriseSunsetOrgInfoResponse> {
        val requestParams = Params(lat, lng, date)
        val call = calls[requestParams]
        return requireNotNull(call) { "Unknown request params: $requestParams" }
    }
}
