package drewhamilton.skylight.views.event

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import java.time.OffsetTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@RequiresApi(Build.VERSION_CODES.O)
fun SkylightEventView.setTime(time: OffsetTime?, @StringRes fallback: Int) =
    setTime(time, fallback = context.getString(fallback))

@RequiresApi(Build.VERSION_CODES.O)
fun SkylightEventView.setTime(time: OffsetTime?, formatter: DateTimeFormatter, @StringRes fallback: Int) =
    setTime(time, formatter, context.getString(fallback))

@RequiresApi(Build.VERSION_CODES.O)
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
