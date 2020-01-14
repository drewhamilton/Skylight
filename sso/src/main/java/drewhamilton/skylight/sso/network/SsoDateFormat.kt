package drewhamilton.skylight.sso.network

import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SsoDate
internal fun LocalDate.toSsoDateString() = DATE_FORMATTER.format(this)

private val DATE_FORMATTER: DateTimeFormatter by lazy { DateTimeFormatter.ISO_LOCAL_DATE }
