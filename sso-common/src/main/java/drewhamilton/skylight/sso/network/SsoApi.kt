package drewhamilton.skylight.sso.network

import drewhamilton.skylight.sso.network.response.SsoInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SsoApi {

    @GET("json")
    fun getInfo(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("date") @SsoDate date: String,
        @Query("formatted") formatted: Int = 0
    ): Call<SsoInfoResponse>
}
