package drewhamilton.skylight.rx

import drewhamilton.skylight.Coordinates
import drewhamilton.skylight.NewSkylightDay
import drewhamilton.skylight.SkylightForDate
import io.reactivex.Single

/**
 * @param coordinates The coordinates to retrieve info for.
 * @return [NewSkylightDay] at the given coordinates.
 */
fun SkylightForDate.getSkylightDaySingle(coordinates: Coordinates) = Single.fromCallable {
    getNewSkylightDay(coordinates)
}
