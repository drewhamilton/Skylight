package drewhamilton.skylight.rx

import java.util.Calendar
import java.util.Date

internal fun Date.toCalendar(): Calendar {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar
}

internal fun Calendar.isSameDay(other: Calendar) =
    this.year == other.year
            && this.month == other.month
            && this.dayOfMonth == other.dayOfMonth

internal val Calendar.year
    get() = get(Calendar.YEAR)

internal val Calendar.month
    get() = get(Calendar.MONTH)

internal val Calendar.dayOfMonth
    get() = get(Calendar.DAY_OF_MONTH)

internal fun todayCalendar() = Calendar.getInstance()

internal fun tomorrowCalendar(): Calendar {
    val tomorrow = Calendar.getInstance()
    tomorrow.add(Calendar.DATE, 1)
    return tomorrow
}
