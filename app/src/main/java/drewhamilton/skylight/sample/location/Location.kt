package drewhamilton.skylight.sample.location

import android.content.Context
import androidx.annotation.StringRes
import drewhamilton.skylight.Coordinates
import drewhamilton.skylight.sample.R
import java.util.TimeZone

data class Location(
    val longDisplayName: String,
    val timeZone: TimeZone,
    val coordinates: Coordinates
) {
    override fun toString(): String {
        return longDisplayName
    }
}

enum class ExampleLocation(
    @StringRes val longDisplayName: Int,
    val timeZone: TimeZone,
    val coordinates: Coordinates
) {
    AMSTERDAM(R.string.amsterdam, TimeZone.getTimeZone("Europe/Amsterdam"), Coordinates(52.3680, 4.9036)),
    BANGKOK(R.string.bangkok, TimeZone.getTimeZone("Asia/Bangkok"), Coordinates(13.7563, 100.5018)),
    HONOLULU(R.string.honolulu, TimeZone.getTimeZone("Pacific/Honolulu"), Coordinates(21.3069, -157.8583)),
    ISTANBUL(R.string.istanbul, TimeZone.getTimeZone("Europe/Istanbul"), Coordinates(41.0082, 28.9784)),
    LIMA(R.string.lima, TimeZone.getTimeZone("America/Lima"), Coordinates(-12.0464, -77.0428)),
    MARRAKESH(R.string.marrakesh, TimeZone.getTimeZone("Africa/Casablanca"), Coordinates(31.6295, -7.9811)),
    NASHVILLE(R.string.nashville, TimeZone.getTimeZone("America/Chicago"), Coordinates(36.1627, -86.7816)),
}

fun ExampleLocation.toLocation(context: Context) =
    Location(context.getString(longDisplayName), timeZone, coordinates)
