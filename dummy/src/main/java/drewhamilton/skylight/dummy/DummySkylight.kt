package drewhamilton.skylight.dummy

import drewhamilton.skylight.Coordinates
import drewhamilton.skylight.NewSkylightDay
import drewhamilton.skylight.Skylight
import java.time.LocalDate
import javax.inject.Inject

/**
 * A [Skylight] implementation that ignores passed parameters, and instead returns specific set values.
 */
class DummySkylight @Inject constructor(
    private val dummySkylightDay: NewSkylightDay
) : Skylight {

    override fun getSkylightDay(coordinates: Coordinates, date: LocalDate) = dummySkylightDay

    /**
     * A convenience overload of [getSkylightDay] with ignored parameters removed.
     * @return the dummy [NewSkylightDay] passed to the constructor.
     */
    fun getSkylightDay() = dummySkylightDay
}
