package drewhamilton.skylight.sso.dates

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Parses and prints dates matching the format: 2015-05-21T19:52:17+00:00
 */
internal class SsoDateFormat: LimitedDateTimeFormat {

  private val actualDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

  override fun format(date: Date): String {
    return actualDateFormat.format(date)
  }

  override fun parse(text: String): Date {
    return actualDateFormat.parse(text)
  }
}
