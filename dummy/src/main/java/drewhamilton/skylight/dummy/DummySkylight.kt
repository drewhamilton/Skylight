package drewhamilton.skylight.dummy

import drewhamilton.skylight.Coordinates
import drewhamilton.skylight.Skylight
import drewhamilton.skylight.SkylightDay
import java.util.Date

/**
 * A [Skylight] implementation that ignores passed parameters, and instead returns specific set values.
 */
class DummySkylight(
    private val dummySkylightDay: SkylightDay
) : Skylight {
    /**
     * @param coordinates ignored.
     * @param date ignored.
     * @return the dummy [SkylightDay] passed to the constructor.
     */
    override fun getSkylightDay(coordinates: Coordinates, date: Date) = dummySkylightDay
}
