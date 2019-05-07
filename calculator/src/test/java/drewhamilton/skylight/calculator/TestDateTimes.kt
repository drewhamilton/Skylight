package drewhamilton.skylight.calculator

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/**
 * All date times in GMT
 */
internal object TestDateTimes {

  private const val DATE_TIME_FORMAT_STRING = "%s %s"

  private const val DATE_JANUARY_6_2019 = "2019-01-06"

  private const val TIME_EARLY = "00:25:55"
  private const val TIME_MORNING = "08:44:22"
  private const val TIME_NOON = "12:00:00"
  private const val TIME_AFTERNOON = "16:04:01"
  private const val TIME_LATE = "23:45:15"

  val JANUARY_6_2019_EARLY = buildDateTimeString(DATE_JANUARY_6_2019, TIME_EARLY)
  val JANUARY_6_2019_MORNING = buildDateTimeString(DATE_JANUARY_6_2019, TIME_MORNING)
  val JANUARY_6_2019_NOON = buildDateTimeString(DATE_JANUARY_6_2019, TIME_NOON)
  val JANUARY_6_2019_AFTERNOON = buildDateTimeString(DATE_JANUARY_6_2019, TIME_AFTERNOON)
  val JANUARY_6_2019_LATE = buildDateTimeString(DATE_JANUARY_6_2019, TIME_LATE)

  val FORMAT: DateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")

  init {
    FORMAT.timeZone = TimeZone.getTimeZone("GMT")
  }

  private fun buildDateTimeString(date: String, time: String) =
      String.format(Locale.US, DATE_TIME_FORMAT_STRING, date, time)
}

internal fun String.asDate(): Date = TestDateTimes.FORMAT.parse(this)
