package dev.drewhamilton.skylight.sso.network

import java.time.ZonedDateTime
import java.time.chrono.IsoChronology
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.ResolverStyle

internal fun @receiver:SsoDateTime String.toZonedDateTime() =
    if (this == ApiConstants.DATE_TIME_NONE || this == ApiConstants.DATE_TIME_ALWAYS_DAY)
        null
    else
        ZonedDateTime.parse(this, DATE_TIME_FORMATTER)

private val DATE_TIME_FORMATTER: DateTimeFormatter by lazy {
    DateTimeFormatterBuilder()
        .parseCaseInsensitive()
        .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        .append(DateTimeFormatter.ofPattern("xxx"))
        .toFormatter()
        .withResolverStyle(ResolverStyle.STRICT)
        .withChronology(IsoChronology.INSTANCE)
}
