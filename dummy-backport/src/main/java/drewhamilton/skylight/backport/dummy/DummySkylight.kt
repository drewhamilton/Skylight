package drewhamilton.skylight.backport.dummy

import drewhamilton.skylight.backport.Coordinates
import drewhamilton.skylight.backport.Skylight
import drewhamilton.skylight.backport.SkylightDay
import org.threeten.bp.LocalDate
import javax.inject.Inject

/**
 * A [Skylight] implementation that ignores the coordinates parameter, and instead returns a copy of a specific
 * [SkylightDay] for the given date parameter.
 */
class DummySkylight @Inject constructor(
    // TODO: Make this public?
    private val dummySkylightDay: SkylightDay
) : Skylight {

    /**
     * Get a copy of the [SkylightDay] originally passed to the constructor for the given [date]. [coordinates] are
     * always ignored.
     */
    override fun getSkylightDay(coordinates: Coordinates, date: LocalDate) = getSkylightDay(date)

    /**
     * Get a copy of the [SkylightDay] originally passed to the constructor for the given [date].
     */
    fun getSkylightDay(date: LocalDate) = dummySkylightDay.copy(date)

    /**
     * Get the dummy [SkylightDay] passed to the constructor.
     */
    @Deprecated("The date parameter is no longer ignored, so this overload is unwanted.")
    fun getSkylightDay() = dummySkylightDay

    private fun SkylightDay.copy(date: LocalDate = this.date): SkylightDay {
        return when (this) {
            is SkylightDay.Typical -> copy(date = date)
            is SkylightDay.AlwaysDaytime -> copy(date = date)
            is SkylightDay.AlwaysLight -> copy(date = date)
            is SkylightDay.NeverDaytime -> copy(date = date)
            is SkylightDay.NeverLight -> copy(date = date)
        }
    }
}
