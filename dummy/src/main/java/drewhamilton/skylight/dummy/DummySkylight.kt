package drewhamilton.skylight.dummy

import drewhamilton.skylight.Coordinates
import drewhamilton.skylight.Skylight
import drewhamilton.skylight.SkylightDay
import java.util.Date
import javax.inject.Inject

/**
 * A [Skylight] implementation that ignores passed parameters, and instead returns specific set values.
 */
class DummySkylight @Inject constructor(
    private val dummySkylightDay: SkylightDay
) : Skylight {

    /**
     * @param coordinates ignored.
     * @param date ignored.
     * @return the dummy [SkylightDay] passed to the constructor.
     */
    override fun getSkylightDay(coordinates: Coordinates, date: Date) = dummySkylightDay

    /**
     * A convenience overload of [getSkylightDay] with ignored parameters removed.
     * @return the dummy [SkylightDay] passed to the constructor.
     */
    fun getSkylightDay() = dummySkylightDay
}
