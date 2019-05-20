@file:Suppress("NewApi")
package drewhamilton.skylight.rx

import drewhamilton.skylight.SkylightDay
import drewhamilton.skylight.SkylightForCoordinates
import io.reactivex.Flowable
import io.reactivex.Single
import java.time.LocalDate

/**
 * @param date The date for which to return info. The time information in this parameter is ignored.
 * @return [SkylightDay] for the given date.
 */
fun SkylightForCoordinates.getSkylightDaySingle(date: LocalDate) = Single.fromCallable {
    getSkylightDay(date)
}

/**
 * @return A [Flowable] that will emit 2 [SkylightDay] instances : 1 for today and 1 for tomorrow.
 */
fun SkylightForCoordinates.getUpcomingSkylightDays(): Flowable<SkylightDay> =
    getSkylightDaySingle(LocalDate.now())
        .mergeWith(getSkylightDaySingle(LocalDate.now().plusDays(1)))
