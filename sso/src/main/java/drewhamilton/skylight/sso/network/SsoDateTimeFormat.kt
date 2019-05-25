package drewhamilton.skylight.sso.network

import java.time.ZonedDateTime
import java.time.chrono.IsoChronology
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.ResolverStyle

@Suppress("NewApi")
internal fun @receiver:SsoDateTime String.toZonedDateTime() = ZonedDateTime.parse(this, DATE_TIME_FORMATTER)

@Suppress("NewApi")
private val DATE_TIME_FORMATTER: DateTimeFormatter by lazy {
    DateTimeFormatterBuilder()
        .parseCaseInsensitive()
        .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        .append(DateTimeFormatter.ofPattern("xxx"))
        .toFormatter()
        .withResolverStyle(ResolverStyle.STRICT)
        .withChronology(IsoChronology.INSTANCE)
}