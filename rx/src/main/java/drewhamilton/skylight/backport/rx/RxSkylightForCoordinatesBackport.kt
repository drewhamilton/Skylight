package drewhamilton.skylight.backport.rx

import drewhamilton.skylight.backport.SkylightDayBackport
import drewhamilton.skylight.backport.SkylightForCoordinatesBackport
import io.reactivex.Flowable
import io.reactivex.Single
import org.threeten.bp.LocalDate


/**
 * @param date The date for which to return info. The time information in this parameter is ignored.
 * @return [SkylightDayBackport] for the given date.
 */
fun SkylightForCoordinatesBackport.getSkylightDaySingle(date: LocalDate) = Single.fromCallable {
    getSkylightDay(date)
}

/**
 * @return A [Flowable] that will emit 2 [SkylightDayBackport] instances : 1 for today and 1 for tomorrow.
 */
fun SkylightForCoordinatesBackport.getUpcomingSkylightDays(): Flowable<SkylightDayBackport> =
    getSkylightDaySingle(LocalDate.now())
        .mergeWith(getSkylightDaySingle(LocalDate.now().plusDays(1)))
