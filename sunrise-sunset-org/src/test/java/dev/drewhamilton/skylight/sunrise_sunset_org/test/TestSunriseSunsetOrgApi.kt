package dev.drewhamilton.skylight.sunrise_sunset_org.test

import dev.drewhamilton.skylight.sunrise_sunset_org.network.SunriseSunsetOrgApi
import dev.drewhamilton.skylight.sunrise_sunset_org.network.request.Params
import dev.drewhamilton.skylight.sunrise_sunset_org.network.response.SunriseSunsetOrgInfoResponse
import retrofit2.Response

internal class TestSunriseSunsetOrgApi(
    private val responses: Map<Params, Response<SunriseSunsetOrgInfoResponse>>,
) : SunriseSunsetOrgApi {

    constructor(vararg pairs: Pair<Params, Response<SunriseSunsetOrgInfoResponse>>) : this(mapOf(*pairs))

    override suspend fun getInfo(
        lat: Double,
        lng: Double,
        date: String,
        formatted: Int,
    ): Response<SunriseSunsetOrgInfoResponse> {
        val requestParams = Params(lat, lng, date)
        val response = responses[requestParams]
        return requireNotNull(response) { "Unknown request params: $requestParams" }
    }
}
