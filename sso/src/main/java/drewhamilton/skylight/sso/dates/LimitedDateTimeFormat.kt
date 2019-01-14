package drewhamilton.skylight.sso.dates

import java.util.*

/**
 * A limited alternative to [java.text.DateFormat].
 */
internal interface LimitedDateTimeFormat {

  fun format(date: Date): String

  fun parse(text: String): Date
}
