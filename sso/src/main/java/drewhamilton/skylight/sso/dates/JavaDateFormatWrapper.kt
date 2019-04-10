package drewhamilton.skylight.sso.dates

import java.text.DateFormat
import java.util.Date
import java.util.TimeZone

internal open class JavaDateFormatWrapper(
    private val javaDateFormat: DateFormat,
    timeZone: TimeZone? = null
) : LimitedDateTimeFormat {

    init {
        if (timeZone != null) javaDateFormat.timeZone = timeZone
    }

    override fun format(date: Date) = javaDateFormat.format(date)!!
    override fun parse(text: String) = javaDateFormat.parse(text)!!
}
