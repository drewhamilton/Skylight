package drewhamilton.skylight.sso.network

import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder

internal object SsoDateTimeFormatters {

    val DATE: DateTimeFormatter by lazy {
        DateTimeFormatter.ISO_LOCAL_DATE
    }

    val DATE_TIME: DateTimeFormatter by lazy {
        DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            .append(DateTimeFormatter.ofPattern("xxx"))
            .toFormatter()
    }
}
