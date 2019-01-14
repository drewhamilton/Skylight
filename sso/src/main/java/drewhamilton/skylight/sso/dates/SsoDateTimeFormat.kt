package drewhamilton.skylight.sso.dates

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Parses and prints dates matching the format: 2015-05-21T19:52:17+00:00
 */
internal class SsoDateTimeFormat: LimitedDateTimeFormat {

  private val actualDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US)

  private val colon = ':'
  private val indexOfTimeZoneColon = "2015-05-21T19:52:17+00:00".lastIndexOf(colon)

  override fun format(date: Date): String {
    val text = actualDateFormat.format(date)
    return text.insertTimeZoneColon()
  }

  override fun parse(text: String): Date {
    val modifiedText = text.removeTimeZoneColon()
    return actualDateFormat.parse(modifiedText)
  }

  private fun String.insertTimeZoneColon(): String {
    return substring(0..indexOfTimeZoneColon) + colon + substring(indexOfTimeZoneColon..length)
  }

  private fun String.removeTimeZoneColon(): String {
    if (this[indexOfTimeZoneColon] == colon) {
      return removeRange(indexOfTimeZoneColon..indexOfTimeZoneColon)
    } else {
      throw ParseException("Colon was not included in time zone as expected", indexOfTimeZoneColon)
    }
  }
}
