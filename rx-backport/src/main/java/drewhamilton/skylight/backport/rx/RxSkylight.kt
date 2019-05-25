package drewhamilton.skylight.backport.rx

import drewhamilton.skylight.backport.Coordinates
import drewhamilton.skylight.backport.SkylightBackport
import drewhamilton.skylight.backport.SkylightDay
import io.reactivex.Flowable
import io.reactivex.Single
import org.threeten.bp.LocalDate

/**
 * @param coordinates The coordinates to retrieve info for.
 * @param date The date for which to return info. The time information in this parameter is ignored.
 * @return [SkylightDay] at the given coordinates for the given date.
 */
fun SkylightBackport.getSkylightDaySingle(coordinates: Coordinates, date: LocalDate) = Single.fromCallable {
    getSkylightDay(coordinates, date)
}

/**
 * @param coordinates The coordinates to retrieve info for.
 * @return A [Flowable] that will emit 2 [SkylightDay] instances at the given coordinates: 1 for today and 1 for
 * tomorrow.
 */
fun SkylightBackport.getUpcomingSkylightDays(coordinates: Coordinates): Flowable<SkylightDay> =
    getSkylightDaySingle(coordinates, LocalDate.now())
        .mergeWith(getSkylightDaySingle(coordinates, LocalDate.now().plusDays(1)))
