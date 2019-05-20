package drewhamilton.skylight.rx

import drewhamilton.skylight.Coordinates
import drewhamilton.skylight.NewSkylightDay
import drewhamilton.skylight.SkylightDay
import drewhamilton.skylight.SkylightForDate
import io.reactivex.Single

/**
 * @param coordinates The coordinates to retrieve info for.
 * @return [NewSkylightDay] at the given coordinates.
 */
fun SkylightForDate.getNewSkylightDaySingle(coordinates: Coordinates) = Single.fromCallable {
    getNewSkylightDay(coordinates)
}

/**
 * @param coordinates The coordinates to retrieve info for.
 * @return [SkylightDay] at the given coordinates.
 */
@Deprecated("Use new one")
fun SkylightForDate.getSkylightDaySingle(coordinates: Coordinates) = Single.fromCallable {
    getSkylightDay(coordinates)
}
