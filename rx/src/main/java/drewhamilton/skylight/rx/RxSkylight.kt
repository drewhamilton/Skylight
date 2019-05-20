@file:Suppress("NewApi")
package drewhamilton.skylight.rx

import drewhamilton.skylight.Coordinates
import drewhamilton.skylight.NewSkylightDay
import drewhamilton.skylight.Skylight
import drewhamilton.skylight.SkylightDay
import io.reactivex.Flowable
import io.reactivex.Single
import java.time.LocalDate
import java.util.Calendar
import java.util.Date

/**
 * @param coordinates The coordinates to retrieve info for.
 * @param date The date for which to return info. The time information in this parameter is ignored.
 * @return [NewSkylightDay] at the given coordinates for the given date.
 */
fun Skylight.getSkylightDaySingle(coordinates: Coordinates, date: LocalDate) = Single.fromCallable {
    getSkylightDay(coordinates, date)
}

/**
 * @param coordinates The coordinates to retrieve info for.
 * @param date The date for which to return info. The time information in this parameter is ignored.
 * @return [SkylightDay] at the given coordinates for the given date.
 */
@Deprecated("Use LocalDate version")
fun Skylight.getSkylightDaySingle(coordinates: Coordinates, date: Date) = Single.fromCallable {
    getSkylightDay(coordinates, date)
}

/**
 * @param coordinates The coordinates to retrieve info for.
 * @return A [Flowable] that will emit 2 [NewSkylightDay] instances at the given coordinates: 1 for today and 1 for
 * tomorrow.
 */
fun Skylight.getUpcomingNewSkylightDays(coordinates: Coordinates): Flowable<NewSkylightDay> =
    getSkylightDaySingle(coordinates, LocalDate.now())
        .mergeWith(getSkylightDaySingle(coordinates, LocalDate.now().plusDays(1)))

/**
 * @param coordinates The coordinates to retrieve info for.
 * @return A [Flowable] that will emit 2 [SkylightDay] instances at the given coordinates: 1 for today and 1 for
 * tomorrow.
 */
@Deprecated("Use LocalDate version")
fun Skylight.getUpcomingSkylightDays(coordinates: Coordinates): Flowable<SkylightDay> =
    getSkylightDaySingle(coordinates, today())
        .mergeWith(getSkylightDaySingle(coordinates, tomorrow()))

internal fun today() = Date()

internal fun tomorrow(): Date {
    val tomorrow = Calendar.getInstance()
    tomorrow.add(Calendar.DATE, 1)
    return tomorrow.time
}
