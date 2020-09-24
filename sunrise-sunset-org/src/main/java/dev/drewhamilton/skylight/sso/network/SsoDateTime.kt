package dev.drewhamilton.skylight.sso.network

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
internal annotation class SsoDateTime
