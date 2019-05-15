package drewhamilton.skylight.sso.network

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import drewhamilton.skylight.sso.datetime.LimitedDateTimeFormat
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.Date
import javax.inject.Inject

class SsoDateTimeAdapter @Inject constructor() {

    private val dateTimeFormat: LimitedDateTimeFormat = SsoDateTimeFormat()
    private val dateFormat: LimitedDateTimeFormat = SsoDateFormat()

    @Deprecated("Replaced by ZonedDateTime-accepting overload")
    @ToJson
    fun dateTimeToString(@SsoDateTime date: Date) = dateTimeFormat.format(date)

    @Deprecated("Replaced by ZonedDateTime-returning overload")
    @FromJson
    @SsoDateTime
    fun dateTimeFromString(dateString: String) = dateTimeFormat.parse(dateString)

    @ToJson
    fun dateTimeToString(@SsoDateTime dateTime: ZonedDateTime): String = SsoDateTimeFormat.get().format(dateTime)

    @FromJson
    @SsoDateTime
    fun newDateTimeFromString(dateString: String): ZonedDateTime =
        ZonedDateTime.parse(dateString, SsoDateTimeFormat.get())

    @Deprecated("Replaced by LocalDate-accepting overload")
    @ToJson
    fun dateToString(@SsoDate date: Date) = dateFormat.format(date)

    @Deprecated("Replaced by LocalDate-returning overload")
    @FromJson
    @SsoDate
    fun dateFromString(dateString: String) = dateFormat.parse(dateString)

    @ToJson
    fun dateToString(@SsoDate date: LocalDate): String = SsoDateFormat.get().format(date)

    @FromJson
    @SsoDate
    fun newDateFromString(dateString: String): LocalDate = LocalDate.parse(dateString, SsoDateFormat.get())
}
