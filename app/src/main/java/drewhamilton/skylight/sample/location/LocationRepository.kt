package drewhamilton.skylight.sample.location

import android.content.Context
import javax.inject.Inject

class LocationRepository @Inject constructor(private val context: Context) {

    fun getLocationOptions() = List(ExampleLocation.values().size) { ExampleLocation.values()[it].toLocation(context) }

    fun getSelectedLocation() = ExampleLocation.AMSTERDAM.toLocation(context)
}
