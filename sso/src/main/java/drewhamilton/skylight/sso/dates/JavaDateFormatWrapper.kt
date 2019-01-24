package drewhamilton.skylight.sso.dates

import java.text.DateFormat
import java.util.*

internal open class JavaDateFormatWrapper(
    private val javaDateFormat: DateFormat,
    timeZone: TimeZone
) : LimitedDateTimeFormat {

    constructor(javaDateFormat: DateFormat) : this(javaDateFormat, TimeZone.getTimeZone("UTC"))

    init {
        javaDateFormat.timeZone = timeZone
    }

    override fun format(date: Date) = javaDateFormat.format(date)!!
    override fun parse(text: String) = javaDateFormat.parse(text)!!
}
