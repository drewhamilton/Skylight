package drewhamilton.skylight.sample.location

import android.content.Context
import androidx.annotation.StringRes
import drewhamilton.skylight.backport.CoordinatesBackport
import drewhamilton.skylight.sample.R
import org.threeten.bp.ZoneId

data class Location(
    val longDisplayName: String,
    val timeZone: ZoneId,
    val coordinates: CoordinatesBackport
) {
    override fun toString(): String {
        return longDisplayName
    }
}

enum class ExampleLocation(
    @StringRes val longDisplayName: Int,
    val timeZone: ZoneId,
    val coordinates: CoordinatesBackport
) {
    AMSTERDAM(R.string.amsterdam, ZoneId.of("Europe/Amsterdam"), CoordinatesBackport(52.3680, 4.9036)),
    BANGKOK(R.string.bangkok, ZoneId.of("Asia/Bangkok"), CoordinatesBackport(13.7563, 100.5018)),
    HONOLULU(R.string.honolulu, ZoneId.of("Pacific/Honolulu"), CoordinatesBackport(21.3069, -157.8583)),
    ISTANBUL(R.string.istanbul, ZoneId.of("Europe/Istanbul"), CoordinatesBackport(41.0082, 28.9784)),
    LIMA(R.string.lima, ZoneId.of("America/Lima"), CoordinatesBackport(-12.0464, -77.0428)),
    MARRAKESH(R.string.marrakesh, ZoneId.of("Africa/Casablanca"), CoordinatesBackport(31.6295, -7.9811)),
    NASHVILLE(R.string.nashville, ZoneId.of("America/Chicago"), CoordinatesBackport(36.1627, -86.7816)),
}

fun ExampleLocation.toLocation(context: Context) =
    Location(context.getString(longDisplayName), timeZone, coordinates)
