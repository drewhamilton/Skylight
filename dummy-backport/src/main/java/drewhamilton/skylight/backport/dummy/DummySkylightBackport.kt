package drewhamilton.skylight.backport.dummy

import drewhamilton.skylight.backport.CoordinatesBackport
import drewhamilton.skylight.backport.SkylightBackport
import drewhamilton.skylight.backport.SkylightDayBackport
import org.threeten.bp.LocalDate
import javax.inject.Inject

/**
 * A [SkylightBackport] implementation that ignores passed parameters, and instead returns specific set values.
 */
class DummySkylightBackport @Inject constructor(
    private val dummySkylightDay: SkylightDayBackport
) : SkylightBackport {

    override fun getSkylightDay(coordinates: CoordinatesBackport, date: LocalDate) = dummySkylightDay

    /**
     * A convenience overload of [getSkylightDay] with ignored parameters removed.
     * @return the dummy [SkylightDayBackport] passed to the constructor.
     */
    fun getSkylightDay() = dummySkylightDay
}
