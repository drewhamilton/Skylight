package drewhamilton.skylight.rx

import drewhamilton.skylight.SkylightDay
import java.util.Calendar
import java.util.Date

private const val timeDifferenceMillis = 5000L

internal fun dummyTypical(dawn: Date) = SkylightDay.Typical(
    dawn,
    Date(dawn.time + timeDifferenceMillis),
    Date(dawn.time + 2 * timeDifferenceMillis),
    Date(dawn.time + 3 * timeDifferenceMillis)
)

internal fun SkylightDay.equalsDummyForDate(date: Calendar) =
    this is SkylightDay.Typical
            && dawn.toCalendar().isSameDay(date)
            && sunrise.toCalendar().isSameDay(date)
            && sunset.toCalendar().isSameDay(date)
            && dusk.toCalendar().isSameDay(date)
