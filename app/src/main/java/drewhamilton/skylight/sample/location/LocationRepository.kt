package drewhamilton.skylight.sample.location

import android.content.Context
import drewhamilton.skylight.models.Coordinates
import javax.inject.Inject

class LocationRepository @Inject constructor(private val context: Context) {

    val svalbard = Location("Svalbard, Norway", "Svalbard", Coordinates(77.8750, 20.9752))

    fun getLocationOptions() = List(ExampleLocation.values().size + 1) {
        if (it < ExampleLocation.values().size)
            ExampleLocation.values()[it].toLocation(context)
        else
            svalbard
    }

    fun getSelectedLocation() = ExampleLocation.AMSTERDAM.toLocation(context)
}
