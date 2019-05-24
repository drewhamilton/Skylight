package drewhamilton.skylight.views.event

import androidx.annotation.StringRes
import org.threeten.bp.OffsetTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

fun SkylightEventView.setTime(time: OffsetTime?, @StringRes fallback: Int) =
    setTime(time, fallback = context.getString(fallback))

fun SkylightEventView.setTime(time: OffsetTime?, formatter: DateTimeFormatter, @StringRes fallback: Int) =
    setTime(time, formatter, context.getString(fallback))

fun SkylightEventView.setTime(
    time: OffsetTime?,
    formatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT),
    fallback: String = ""
) {
    if (time == null) {
        timeHint = fallback
        timeText = ""
    } else {
        timeText = formatter.format(time)
        timeHint = ""
    }
}
