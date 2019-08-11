package drewhamilton.skylight.rx

import drewhamilton.skylight.SkylightDay
import java.time.LocalDate
import java.time.OffsetTime
import java.time.ZonedDateTime

private const val timeDifferenceSeconds = 5L

internal fun dummyTypical(date: LocalDate, dawn: OffsetTime) = SkylightDay.Typical(
    zonedDateTimeOf(date, dawn),
    zonedDateTimeOf(date, dawn.plusSeconds(timeDifferenceSeconds)),
    zonedDateTimeOf(date, dawn.plusSeconds(2 * timeDifferenceSeconds)),
    zonedDateTimeOf(date, dawn.plusSeconds(3 * timeDifferenceSeconds))
)

private fun zonedDateTimeOf(date: LocalDate, time: OffsetTime) = ZonedDateTime.of(date, time.toLocalTime(), time.offset)
