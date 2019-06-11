package drewhamilton.skylight.views.event

import androidx.annotation.StringRes
import org.threeten.bp.Instant
import org.threeten.bp.OffsetTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

fun SkylightEventView.setTime(time: OffsetTime?, @StringRes fallback: Int) =
    setTime(time, fallback = context.getString(fallback))

fun SkylightEventView.setTime(time: OffsetTime?, formatter: DateTimeFormatter, @StringRes fallback: Int) =
    setTime(time, formatter, fallback = context.getString(fallback))

fun SkylightEventView.setTime(
    time: OffsetTime?,
    formatter: DateTimeFormatter,
    timeZone: ZoneOffset,
    @StringRes fallback: Int
) = setTime(time, formatter, timeZone, context.getString(fallback))

fun SkylightEventView.setTime(
    time: OffsetTime?,
    formatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT),
    timeZone: ZoneOffset = ZoneId.systemDefault().rules.getOffset(Instant.now()),
    fallback: String = ""
) {
    if (time == null) {
        timeHint = fallback
        timeText = ""
    } else {
        val timeInZone = time.withOffsetSameInstant(timeZone)
        timeText = formatter.format(timeInZone)
        timeHint = ""
    }
}
