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
    fun getSkylightDay(coordinates: Coordinates) = skylight.getSkylightDay(coordinates, date)
}
