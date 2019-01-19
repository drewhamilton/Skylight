package drewhamilton.skylight

import drewhamilton.skylight.models.*
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*

interface RxSkylightRepository {

    /**
     * @param coordinates The coordinates to retrieve info for.
     * @param date The date for which to return info. The time information in this parameter is ignored.
     * @return [SkylightInfo] at the given coordinates for the given date.
     */
    fun getSkylightInfo(coordinates: Coordinates, date: Date): Single<SkylightInfo>

}

/**
 * @param coordinates The coordinates to retrieve info for.
 * @return [SkylightInfo] at the given coordinates for today and tomorrow.
 */
fun RxSkylightRepository.getUpcomingSkylightInfo(coordinates: Coordinates): Flowable<SkylightInfo> =
  getSkylightInfo(coordinates, today())
      .mergeWith(getSkylightInfo(coordinates, tomorrow()))

/**
 * @param coordinates The coordinates for which lightness should be determined.
 * @param dateTime The date-time at which to check for lightness.
 * @return Whether it is light outside at the given coordinates at the given date-time, where "light" means after dawn
 * and before dusk on the given date.
 */
fun RxSkylightRepository.isLight(coordinates: Coordinates, dateTime: Date): Single<Boolean> =
  getSkylightInfo(coordinates, dateTime)
      .map {
        when (it) {
          is AlwaysDaytime -> true
          is AlwaysLight -> true
          is NeverLight -> false
          is NeverDaytime -> isLight(it.dawn, it.dusk, dateTime)
          is Typical -> isLight(it.dawn, it.dusk, dateTime)
    }
  }

/**
 * @param coordinates The coordinates for which darkness should be determined.
 * @param dateTime The date-time at which to check for darkness.
 * @return Whether it is dark outside at the given coordinates at the given date-time, where "dark" means before dawn
 * or after dusk on the given date.
 */
fun RxSkylightRepository.isDark(coordinates: Coordinates, dateTime: Date) : Single<Boolean> =
    isLight(coordinates, dateTime)
        .map { !it }

private fun isLight(dawn: Date, dusk: Date, dateTime: Date) = dawn.before(dateTime) && dusk.after(dateTime)

private fun today() = Date()

private fun tomorrow(): Date {
    val tomorrow = Calendar.getInstance()
    tomorrow.add(Calendar.DATE, 1)
    return tomorrow.time
}
