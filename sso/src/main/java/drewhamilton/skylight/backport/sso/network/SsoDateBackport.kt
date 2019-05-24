package drewhamilton.skylight.backport.sso.network

import drewhamilton.skylight.sso.network.SsoDate
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

@SsoDate
internal fun LocalDate.toSsoDateString() = DATE_FORMATTER.format(this)

private val DATE_FORMATTER: DateTimeFormatter by lazy { DateTimeFormatter.ISO_LOCAL_DATE }
