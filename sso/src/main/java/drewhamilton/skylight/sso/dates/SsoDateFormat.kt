package drewhamilton.skylight.sso.dates

import java.text.SimpleDateFormat
import java.util.*

/**
 * Parses and prints dates matching the format: 2015-05-21
 */
internal class SsoDateFormat: JavaDateFormatWrapper(SimpleDateFormat("yyyy-MM-dd", Locale.US))
