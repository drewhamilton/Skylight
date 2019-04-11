package drewhamilton.skylight.sso.network

import drewhamilton.skylight.sso.datetime.JavaDateFormatWrapper
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

/**
 * Parses and prints dates matching the format: 2015-05-21
 */
internal class SsoDateFormat :
    JavaDateFormatWrapper(SimpleDateFormat("yyyy-MM-dd", Locale.US), TimeZone.getTimeZone("UTC"))
