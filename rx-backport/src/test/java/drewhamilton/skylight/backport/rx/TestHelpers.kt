package drewhamilton.skylight.backport.rx

import drewhamilton.skylight.backport.SkylightDayBackport
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetTime

private const val timeDifferenceSeconds = 5L

internal fun dummyTypical(date: LocalDate, dawn: OffsetTime) = SkylightDayBackport.Typical(
    date,
    dawn,
    dawn.plusSeconds(timeDifferenceSeconds),
    dawn.plusSeconds(2 * timeDifferenceSeconds),
    dawn.plusSeconds(3 * timeDifferenceSeconds)
)
