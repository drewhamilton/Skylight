package dev.drewhamilton.skylight.sso.network

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
internal annotation class SsoDate
