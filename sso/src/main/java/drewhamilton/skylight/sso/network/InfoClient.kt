package drewhamilton.skylight.sso.network

import drewhamilton.skylight.sso.serialization.SsoDateTimeAdapter
import drewhamilton.skylight.sso.network.models.Params
import drewhamilton.skylight.sso.network.models.SunriseSunsetInfo
import io.reactivex.Single
import javax.inject.Inject

class InfoClient @Inject constructor(
    private val api: SsoApi,
    private val dateTimeAdapter: SsoDateTimeAdapter
) {

  private val formatted = 0

  fun getInfo(params: Params): Single<SunriseSunsetInfo> {
    return api.getInfo(params.lat, params.lng, dateTimeAdapter.dateToString(params.date), formatted)
        .map { it.results }
  }
}
