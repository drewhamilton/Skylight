package drewhamilton.skylight.views.event

import androidx.annotation.StringRes
import java.text.DateFormat
import java.util.Date

/**
 * Set the view's time text to [time], formatted in the default format. Fall back to [fallback] as hint text if the
 * given time is null.
 */
fun SkylightEventView.setTime(time: Date?, @StringRes fallback: Int) =
    setTime(time, fallback = context.getString(fallback))

/**
 * Set the view's time text to [dateTime], formatted in the given [format]. Fall back to [fallback] as hint text if the
 * given date-time is null.
 */
fun SkylightEventView.setTime(dateTime: Date?, format: DateFormat, @StringRes fallback: Int) =
    setTime(dateTime, format, context.getString(fallback))

/**
 * Set the view's time text to [dateTime], formatted in the given [format]. Fall back to [fallback] as hint text if the
 * given date-time is null.
 */
fun SkylightEventView.setTime(
    dateTime: Date?,
    format: DateFormat = DateFormat.getTimeInstance(DateFormat.SHORT),
    fallback: String = ""
) {
    if (dateTime == null) {
        timeHint = fallback
        timeText = ""
    } else {
        timeText = format.format(dateTime)
        timeHint = ""
    }
}
