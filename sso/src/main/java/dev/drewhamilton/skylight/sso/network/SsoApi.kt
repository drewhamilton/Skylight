package dev.drewhamilton.skylight.sso.network

import dev.drewhamilton.skylight.sso.network.response.SsoInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

internal interface SsoApi {

    @GET("json")
    fun getInfo(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("date") @SsoDate date: String,
        @Query("formatted") formatted: Int = 0
    ): Call<SsoInfoResponse>
}
