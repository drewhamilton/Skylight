package drewhamilton.skylight.sso.network

import java.time.ZonedDateTime
import java.time.chrono.IsoChronology
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.ResolverStyle

/**
 * Indicates a String in the sunrise-sunset.org API's date-time format, e.g. "2019-05-21T19:15:34+00:00". (Format is
 * different if "formatted=1" is used in the request.)
 */
@Target(
    AnnotationTarget.FIELD,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.LOCAL_VARIABLE,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.TYPE,
    AnnotationTarget.VALUE_PARAMETER
)
annotation class SsoDateTime

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
