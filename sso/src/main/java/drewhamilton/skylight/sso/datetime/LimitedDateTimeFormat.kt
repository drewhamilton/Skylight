package drewhamilton.skylight.sso.datetime

import java.util.Date

/**
 * A limited alternative to [java.text.DateFormat]. Allows for simple implementations without having to cover all
 * the logic [java.text.DateFormat]s are expected to cover.
 */
@Deprecated("Unneeded with java.time")
internal interface LimitedDateTimeFormat {

    fun format(date: Date): String

    fun parse(text: String): Date
}
