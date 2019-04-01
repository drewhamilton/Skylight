package drewhamilton.skylight.sso.network

import drewhamilton.skylight.sso.network.models.Params
import drewhamilton.skylight.sso.network.models.SunriseSunsetInfo
import drewhamilton.skylight.sso.serialization.SsoDateTimeAdapter
import javax.inject.Inject

class InfoClient @Inject constructor(
    private val api: SsoApi,
    private val dateTimeAdapter: SsoDateTimeAdapter
) {

  private val formatted = 0

  fun getInfo(params: Params): SunriseSunsetInfo {
      val response = api.getInfo(params.lat, params.lng, dateTimeAdapter.dateToString(params.date), formatted)
      return response.results
  }
}
