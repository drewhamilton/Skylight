package drewhamilton.skylight.sample.location

import android.content.Context

class LocationRepository(private val context: Context) {

    fun getLocationOptions() = List(ExampleLocation.values().size) { ExampleLocation.values()[it].toLocation(context) }
}
