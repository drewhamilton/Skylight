@file:Suppress("NewApi")
package drewhamilton.skylight.rx

import drewhamilton.skylight.NewSkylightDay
import drewhamilton.skylight.SkylightDay
import drewhamilton.skylight.SkylightForCoordinates
import io.reactivex.Flowable
import io.reactivex.Single
import java.time.LocalDate
import java.util.Date

/**
 * @param date The date for which to return info. The time information in this parameter is ignored.
 * @return [NewSkylightDay] for the given date.
 */
fun SkylightForCoordinates.getSkylightDaySingle(date: LocalDate) = Single.fromCallable {
    getSkylightDay(date)
}

/**
 * @param date The date for which to return info. The time information in this parameter is ignored.
 * @return [SkylightDay] for the given date.
 */
@Deprecated("Use LocalDate version")
fun SkylightForCoordinates.getSkylightDaySingle(date: Date) = Single.fromCallable {
    getSkylightDay(date)
}

/**
 * @return A [Flowable] that will emit 2 [NewSkylightDay] instances : 1 for today and 1 for tomorrow.
 */
fun SkylightForCoordinates.getUpcomingNewSkylightDays(): Flowable<NewSkylightDay> =
    getSkylightDaySingle(LocalDate.now())
        .mergeWith(getSkylightDaySingle(LocalDate.now().plusDays(1)))

/**
 * @return A [Flowable] that will emit 2 [SkylightDay] instances : 1 for today and 1 for tomorrow.
 */
@Deprecated("Use LocalDate version")
fun SkylightForCoordinates.getUpcomingSkylightDays(): Flowable<SkylightDay> =
    getSkylightDaySingle(today())
        .mergeWith(getSkylightDaySingle(tomorrow()))
