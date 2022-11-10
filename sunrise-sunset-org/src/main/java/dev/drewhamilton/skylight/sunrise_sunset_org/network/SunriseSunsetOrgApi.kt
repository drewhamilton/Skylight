package dev.drewhamilton.skylight.sunrise_sunset_org.network

import dev.drewhamilton.skylight.sunrise_sunset_org.network.response.SunriseSunsetOrgInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface SunriseSunsetOrgApi {

    @GET("json")
    suspend fun getInfo(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("date") @SunriseSunsetOrgDate date: String,
        @Query("formatted") formatted: Int = 0
    ): Response<SunriseSunsetOrgInfoResponse>
}
