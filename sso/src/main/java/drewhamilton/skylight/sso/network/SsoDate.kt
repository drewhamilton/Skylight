package drewhamilton.skylight.sso.network

import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Indicates a String in the sunrise-sunset.org API's date format, e.g. "2019-05-21"
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
annotation class SsoDate

@Suppress("NewApi")
@SsoDate
internal fun LocalDate.toSsoDateString() = DATE_FORMATTER.format(this)

@Suppress("NewApi")
private val DATE_FORMATTER: DateTimeFormatter by lazy { DateTimeFormatter.ISO_LOCAL_DATE }
