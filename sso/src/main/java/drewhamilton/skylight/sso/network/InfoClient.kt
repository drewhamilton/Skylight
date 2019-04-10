package drewhamilton.skylight.sso.network

import drewhamilton.skylight.sso.network.request.Params
import drewhamilton.skylight.sso.network.response.SunriseSunsetInfo
import retrofit2.HttpException
import javax.inject.Inject

class InfoClient @Inject constructor(
    private val api: SsoApi,
    private val dateTimeAdapter: SsoDateTimeAdapter
) {

    private val formatted = 0

    fun getInfo(params: Params): SunriseSunsetInfo {
        val call = api.getInfo(params.lat, params.lng, dateTimeAdapter.dateToString(params.date), formatted)

        val response = call.execute()
        val responseBody = response.body()
        if (response.isSuccessful && responseBody != null)
            return responseBody.results
        else
            throw HttpException(response)
    }
}
