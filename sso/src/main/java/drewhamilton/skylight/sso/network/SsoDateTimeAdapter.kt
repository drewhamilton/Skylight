package drewhamilton.skylight.sso.network

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDate
import java.time.ZonedDateTime
import javax.inject.Inject

class SsoDateTimeAdapter @Inject constructor() {

    @ToJson
    fun dateTimeToString(@SsoDateTime dateTime: ZonedDateTime): String =
        SsoDateTimeFormatters.DATE_TIME.format(dateTime)

    @FromJson
    @SsoDateTime
    fun dateTimeFromString(dateString: String): ZonedDateTime =
        ZonedDateTime.parse(dateString, SsoDateTimeFormatters.DATE_TIME)

    @ToJson
    fun dateToString(@SsoDate date: LocalDate): String = SsoDateTimeFormatters.DATE.format(date)

    @FromJson
    @SsoDate
    fun dateFromString(dateString: String): LocalDate = LocalDate.parse(dateString, SsoDateTimeFormatters.DATE)
}
