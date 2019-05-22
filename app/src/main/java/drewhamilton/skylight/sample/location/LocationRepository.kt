package drewhamilton.skylight.sample.location

import android.content.Context
import drewhamilton.skylight.backport.Coordinates
import org.threeten.bp.ZoneId
import javax.inject.Inject

class LocationRepository @Inject constructor(private val context: Context) {

    private val svalbard =
        Location("Svalbard, Norway", ZoneId.of("Europe/Oslo"), Coordinates(77.8750, 20.9752))

    fun getLocationOptions() = List(ExampleLocation.values().size + 1) {
        if (it < ExampleLocation.values().size)
            ExampleLocation.values()[it].toLocation(context)
        else
            svalbard
    }
}
