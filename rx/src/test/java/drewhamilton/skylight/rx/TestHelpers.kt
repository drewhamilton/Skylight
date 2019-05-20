package drewhamilton.skylight.rx

import drewhamilton.skylight.NewSkylightDay
import java.time.LocalDate
import java.time.OffsetTime

private const val timeDifferenceSeconds = 5L

internal fun dummyTypical(date: LocalDate, dawn: OffsetTime) = NewSkylightDay.Typical(
    date,
    dawn,
    dawn.plusSeconds(timeDifferenceSeconds),
    dawn.plusSeconds(2 * timeDifferenceSeconds),
    dawn.plusSeconds(3 * timeDifferenceSeconds)
)

internal fun NewSkylightDay.equalsDummyForDate(date: LocalDate) =
    this is NewSkylightDay.Typical && this.date == date
