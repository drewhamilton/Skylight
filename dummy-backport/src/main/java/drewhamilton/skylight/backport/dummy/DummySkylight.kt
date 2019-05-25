package drewhamilton.skylight.backport.dummy

import drewhamilton.skylight.backport.Coordinates
import drewhamilton.skylight.backport.SkylightBackport
import drewhamilton.skylight.backport.SkylightDay
import org.threeten.bp.LocalDate
import javax.inject.Inject

/**
 * A [SkylightBackport] implementation that ignores passed parameters, and instead returns specific set values.
 */
class DummySkylight @Inject constructor(
    private val dummySkylightDay: SkylightDay
) : SkylightBackport {

    override fun getSkylightDay(coordinates: Coordinates, date: LocalDate) = dummySkylightDay

    /**
     * A convenience overload of [getSkylightDay] with ignored parameters removed.
     * @return the dummy [SkylightDay] passed to the constructor.
     */
    fun getSkylightDay() = dummySkylightDay
}
