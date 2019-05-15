package drewhamilton.skylight.sso.network

import drewhamilton.skylight.sso.network.request.NewParams
import drewhamilton.skylight.sso.network.request.Params
import drewhamilton.skylight.sso.network.response.NewSunriseSunsetInfo
import drewhamilton.skylight.sso.network.response.SunriseSunsetInfo
import retrofit2.HttpException
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.Date
import javax.inject.Inject

class InfoClient @Inject constructor(
    private val api: SsoApi,
    private val dateTimeAdapter: SsoDateTimeAdapter
) {

    private val formatted = 0

    fun getInfo(params: NewParams): NewSunriseSunsetInfo {
        val call = api.getInfo(params.lat, params.lng, dateTimeAdapter.dateToString(params.date), formatted)

        val response = call.execute()
        val responseBody = response.body()
        if (response.isSuccessful && responseBody != null)
            return responseBody.results
        else
            throw HttpException(response)
    }

    @Deprecated("Replaced by NewParams overload")
    fun getInfo(params: Params): SunriseSunsetInfo {
        val convertedDate: LocalDate = dateTimeAdapter.newDateFromString(dateTimeAdapter.dateToString(params.date))
        val info = getInfo(NewParams(params.lat, params.lng, convertedDate))
        return SunriseSunsetInfo(info.sunrise.toDate(), info.sunset.toDate(),
            info.civil_twilight_begin.toDate(), info.civil_twilight_end.toDate())
    }

    private fun ZonedDateTime.toDate() = Date(toInstant().toEpochMilli())
}
