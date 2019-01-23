package drewhamilton.skylight.sso.serialization

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import drewhamilton.skylight.sso.dates.LimitedDateTimeFormat
import drewhamilton.skylight.sso.dates.SsoDateFormat
import drewhamilton.skylight.sso.dates.SsoDateTimeFormat
import java.util.*
import javax.inject.Inject

class SsoDateTimeAdapter @Inject constructor() {

    private val dateTimeFormat: LimitedDateTimeFormat = SsoDateTimeFormat()
    private val dateFormat: LimitedDateTimeFormat = SsoDateFormat()

    @ToJson
    fun dateTimeToString(@SsoDateTime date: Date) = dateTimeFormat.format(date)

    @FromJson
    @SsoDateTime
    fun dateTimeFromString(dateString: String) = dateTimeFormat.parse(dateString)

    @ToJson
    fun dateToString(@SsoDate date: Date) = dateFormat.format(date)

    @FromJson
    @SsoDate
    fun dateFromString(dateString: String) = dateFormat.parse(dateString)
}
