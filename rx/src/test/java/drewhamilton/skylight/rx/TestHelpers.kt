package drewhamilton.skylight.rx

import drewhamilton.skylight.SkylightDay
import java.time.LocalDate
import java.time.OffsetTime

private const val timeDifferenceSeconds = 5L

internal fun dummyTypical(date: LocalDate, dawn: OffsetTime) = SkylightDay.Typical(
    date,
    dawn,
    dawn.plusSeconds(timeDifferenceSeconds),
    dawn.plusSeconds(2 * timeDifferenceSeconds),
    dawn.plusSeconds(3 * timeDifferenceSeconds)
)

internal fun SkylightDay.equalsDummyForDate(date: LocalDate) =
    this is SkylightDay.Typical && this.date == date
