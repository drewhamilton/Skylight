package drewhamilton.skylight.rx

import drewhamilton.skylight.Skylight
import drewhamilton.skylight.Coordinates
import drewhamilton.skylight.SkylightInfo
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.Calendar
import java.util.Date

/**
 * @param coordinates The coordinates to retrieve info for.
 * @param date The date for which to return info. The time information in this parameter is ignored.
 * @return [SkylightInfo] at the given coordinates for the given date.
 */
fun Skylight.getSkylightInfoSingle(coordinates: Coordinates, date: Date) = Single.fromCallable {
    determineSkylightInfo(coordinates, date)
}

/**
 * @param coordinates The coordinates to retrieve info for.
 * @return A [Flowable] that will emit 2 [SkylightInfo] instances at the given coordinates: 1 for today and 1 for
 * tomorrow.
 */
fun Skylight.getUpcomingSkylightInfoFlowable(coordinates: Coordinates): Flowable<SkylightInfo> =
    getSkylightInfoSingle(coordinates, today())
        .mergeWith(getSkylightInfoSingle(coordinates, tomorrow()))

private fun today() = Date()

private fun tomorrow(): Date {
    val tomorrow = Calendar.getInstance()
    tomorrow.add(Calendar.DATE, 1)
    return tomorrow.time
}
