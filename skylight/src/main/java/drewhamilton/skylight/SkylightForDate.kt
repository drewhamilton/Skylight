package drewhamilton.skylight

import java.util.Date
import javax.inject.Inject

/**
 * A convenient [Skylight] wrapper for a single date.
 */
class SkylightForDate @Inject constructor(
    private val skylight: Skylight,
    val date: Date
) {
    /**
     * @param coordinates The coordinates for which to return info.
     * @return [SkylightDay] on this object's date at the given coordinates.
     */
    fun getSkylightDay(coordinates: Coordinates) = skylight.getSkylightDay(coordinates, date)
}
