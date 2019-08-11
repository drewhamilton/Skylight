package drewhamilton.skylight.backport.sso.network

import drewhamilton.skylight.sso.network.SsoDateTime
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.chrono.IsoChronology
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DateTimeFormatterBuilder
import org.threeten.bp.format.ResolverStyle

internal fun @receiver:SsoDateTime String.toZonedDateTime() = ZonedDateTime.parse(this, DATE_TIME_FORMATTER)

private val DATE_TIME_FORMATTER: DateTimeFormatter by lazy {
    DateTimeFormatterBuilder()
        .parseCaseInsensitive()
        .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        .append(DateTimeFormatter.ofPattern("xxx"))
        .toFormatter()
        .withResolverStyle(ResolverStyle.STRICT)
        .withChronology(IsoChronology.INSTANCE)
}
