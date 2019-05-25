package drewhamilton.skylight.sso.network

import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Suppress("NewApi")
@SsoDate
internal fun LocalDate.toSsoDateString() = DATE_FORMATTER.format(this)

@Suppress("NewApi")
private val DATE_FORMATTER: DateTimeFormatter by lazy { DateTimeFormatter.ISO_LOCAL_DATE }
