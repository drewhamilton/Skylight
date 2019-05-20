@file:Suppress("NewApi")
package drewhamilton.skylight.rx

import drewhamilton.skylight.Coordinates
import drewhamilton.skylight.NewSkylightDay
import drewhamilton.skylight.Skylight
import io.reactivex.Flowable
import io.reactivex.Single
import java.time.LocalDate

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
 * @return A [Flowable] that will emit 2 [NewSkylightDay] instances at the given coordinates: 1 for today and 1 for
 * tomorrow.
 */
fun Skylight.getUpcomingSkylightDays(coordinates: Coordinates): Flowable<NewSkylightDay> =
    getSkylightDaySingle(coordinates, LocalDate.now())
        .mergeWith(getSkylightDaySingle(coordinates, LocalDate.now().plusDays(1)))
