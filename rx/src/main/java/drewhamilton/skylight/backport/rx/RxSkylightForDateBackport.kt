package drewhamilton.skylight.backport.rx

import drewhamilton.skylight.backport.Coordinates
import drewhamilton.skylight.backport.SkylightDayBackport
import drewhamilton.skylight.backport.SkylightForDateBackport
import io.reactivex.Single

/**
 * @param coordinates The coordinates to retrieve info for.
 * @return [SkylightDayBackport] at the given coordinates.
 */
fun SkylightForDateBackport.getSkylightDaySingle(coordinates: Coordinates) = Single.fromCallable {
    getSkylightDay(coordinates)
}
