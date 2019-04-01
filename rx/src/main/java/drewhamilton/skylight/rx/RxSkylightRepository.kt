package drewhamilton.skylight.rx

import drewhamilton.skylight.SkylightRepository
import drewhamilton.skylight.models.Coordinates
import drewhamilton.skylight.models.SkylightInfo
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.Calendar
import java.util.Date

/**
 * @param coordinates The coordinates to retrieve info for.
 * @param date The date for which to return info. The time information in this parameter is ignored.
 * @return [SkylightInfo] at the given coordinates for the given date.
 */
fun SkylightRepository.skylightInfo(coordinates: Coordinates, date: Date) = Single.fromCallable {
    getSkylightInfo(coordinates, date)
}

/**
 * @param coordinates The coordinates to retrieve info for.
 * @return A [Flowable] that will emit 2 [SkylightInfo] instances at the given coordinates: 1 for today and 1 for
 * tomorrow.
 */
fun SkylightRepository.upcomingSkylightInfo(coordinates: Coordinates): Flowable<SkylightInfo> =
    skylightInfo(coordinates, today())
        .mergeWith(skylightInfo(coordinates, tomorrow()))

private fun today() = Date()

private fun tomorrow(): Date {
    val tomorrow = Calendar.getInstance()
    tomorrow.add(Calendar.DATE, 1)
    return tomorrow.time
}
