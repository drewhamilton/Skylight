package drewhamilton.skylight.sso.serialization

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import drewhamilton.skylight.sso.dates.SsoDateFormat
import drewhamilton.skylight.sso.dates.SsoDateTimeFormat
import java.util.*
import javax.inject.Inject

class SsoDateTimeAdapter @Inject constructor() {

  private val dateTimeFormat = SsoDateTimeFormat()
  private val dateFormat = SsoDateFormat()

  @ToJson
  fun dateTimeToString(@SsoDateTime date: Date): String {
    return dateTimeFormat.format(date)
  }

  @FromJson
  @SsoDateTime
  fun dateTimeFromString(dateString: String): Date {
    return dateTimeFormat.parse(dateString)
  }

  @ToJson
  fun dateToString(@SsoDate date: Date): String {
    return dateFormat.format(date)
  }

  @FromJson
  @SsoDate
  fun dateFromString(dateString: String): Date {
    return dateFormat.parse(dateString)
  }
}
