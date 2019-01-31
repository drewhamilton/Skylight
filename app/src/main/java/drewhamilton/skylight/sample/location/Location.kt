package drewhamilton.skylight.sample.location

import android.content.Context
import androidx.annotation.StringRes
import drewhamilton.skylight.models.Coordinates
import drewhamilton.skylight.sample.R
import java.util.*

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
    AMSTERDAM(R.string.amsterdam_long, TimeZone.getTimeZone("Europe/Amsterdam"), Coordinates(52.3680, 4.9036)),
    CARY(R.string.cary_long, TimeZone.getTimeZone("America/Chicago"), Coordinates(42.2132, -88.2477)),
    FINDLAY(R.string.findlay_long, TimeZone.getTimeZone("America/New_York"), Coordinates(41.0442, -83.6499)),
    GALLIATE_LOMBARDO(R.string.galliateLombardo_long, TimeZone.getTimeZone("Europe/Rome"), Coordinates(45.7887, 8.7706)),
    MADISON(R.string.madison_long, TimeZone.getTimeZone("America/Chicago"), Coordinates(43.0731, -89.4012)),
    METZ(R.string.metz_long, TimeZone.getTimeZone("Europe/Paris"), Coordinates(49.1193, 6.1757)),
    NASHVILLE(R.string.nashville_long, TimeZone.getTimeZone("America/Chicago"), Coordinates(36.1627, -86.7816)),
    SAINT_JOSEPH(R.string.saintJoseph_long, TimeZone.getTimeZone("America/Detroit"), Coordinates(42.0939, -86.4895))
}

fun ExampleLocation.toLocation(context: Context) =
    Location(context.getString(longDisplayName), timeZone, coordinates)
