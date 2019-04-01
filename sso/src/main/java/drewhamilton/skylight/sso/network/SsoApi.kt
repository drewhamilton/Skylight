package drewhamilton.skylight.sso.network

import drewhamilton.skylight.sso.network.models.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SsoApi {

  @GET("json")
  fun getInfo(
      @Query("lat") lat: Double,
      @Query("lng") lng: Double,
      @Query("date") date: String,
      @Query("formatted") formatted: Int
  ): Response
}
