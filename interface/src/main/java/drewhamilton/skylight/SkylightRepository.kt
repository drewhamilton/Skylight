package drewhamilton.skylight

import drewhamilton.skylight.models.*
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*

// TODO Should probably be an interface?
abstract class SkylightRepository {

  /**
   * Get [SkylightInfo] at the given [coordinates] for the given [date].
   */
  abstract fun getSkylightInfo(coordinates: Coordinates, date: Date): Single<SkylightInfo>

  /**
   * Gets [SkylightInfo] at the given [coordinates] for today and tomorrow.
   *
   * TODO Should be an extension function
   */
  fun getUpcomingSkylightInfo(coordinates: Coordinates): Flowable<SkylightInfo> =
      getSkylightInfo(coordinates, today())
          .mergeWith(getSkylightInfo(coordinates, tomorrow()))

  /**
   * Returns whether it is light outside at the given [coordinates] at the given [dateTime].
   *
   * TODO Should be an extension function
   */
  fun isLight(coordinates: Coordinates, dateTime: Date): Single<Boolean> =
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

  private fun isLight(dawn: Date, dusk: Date, dateTime: Date) = dawn.before(dateTime) && dusk.after(dateTime)

  private fun today() = Date()

  private fun tomorrow(): Date {
    val tomorrow = Calendar.getInstance()
    tomorrow.add(Calendar.DATE, 1)
    return tomorrow.time
  }
}
