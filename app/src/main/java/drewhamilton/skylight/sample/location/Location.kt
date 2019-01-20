package drewhamilton.skylight.sample.location

import android.content.Context
import androidx.annotation.StringRes
import drewhamilton.skylight.models.Coordinates
import drewhamilton.skylight.sample.R

data class Location(
    val longDisplayName: String,
    val shortDisplayName: String,
    val coordinates: Coordinates
) {
    override fun toString(): String {
        return longDisplayName
    }
}

enum class ExampleLocation(
    @StringRes val longDisplayName: Int,
    @StringRes val shortDisplayName: Int,
    val coordinates: Coordinates
) {
    AMSTERDAM(R.string.amsterdam_long, R.string.amsterdam_short, Coordinates(52.3680, 4.9036)),
    CARY(R.string.cary_long, R.string.cary_short, Coordinates(42.2132, 88.2477)),
    FINDLAY(R.string.findlay_long, R.string.findlay_short, Coordinates(41.0442, 83.6499)),
    GALLIATE_LOMBARDO(R.string.galliateLombardo_long, R.string.galliateLombardo_short, Coordinates(45.7887, 8.7706)),
    MADISON(R.string.madison_long, R.string.madison_short, Coordinates(43.0731, 89.4012)),
    METZ(R.string.metz_long, R.string.metz_short, Coordinates(49.1193, 6.1757)),
    NASHVILLE(R.string.nashville_long, R.string.nashville_short, Coordinates(36.1627, 86.7816)),
    SAINT_JOSEPH(R.string.saintJoseph_long, R.string.saintJoseph_short, Coordinates(42.0939, 86.4895))
}

fun ExampleLocation.toLocation(context: Context) =
    Location(context.getString(longDisplayName), context.getString(shortDisplayName), coordinates)
