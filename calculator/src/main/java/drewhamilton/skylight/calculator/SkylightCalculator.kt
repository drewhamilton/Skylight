package drewhamilton.skylight.calculator

import dagger.Reusable
import drewhamilton.skylight.SkylightDay
import java.util.Date
import javax.inject.Inject

/**
 * Adapted from AndroidX's (internal) TwilightCalculator class.
 *
 * Values returned are a bit "fuzzy"; that is, you can get different SkylightInfo for the same location on the same day
 * depending on the exact input time. This difference has been noted to range up to almost 1 minute, but has not been
 * tested extensively. Handle the returned calculation accordingly
 */
@Reusable
class SkylightCalculator @Inject constructor() {

    private val degreesToRadians = (Math.PI / 180.0f).toFloat()
    private val dayInMillis = 1000 * 60 * 60 * 24;

    // Java time on Jan 1, 2000 12:00 UTC.
    private val unixNoonUtc2000 = 946728000000L

    private val solarTransitCorrection = 0.0009
    private val c1 = 0.0334196f
    private val c2 = 0.000349066f
    private val c3 = 0.000005236f
    private val obliquity = 0.40927971f

    // This is the same altitude TwilightCalculator uses for civil twilight:
    private val altitudeRadiansCivilTwilight = -0.104719755f

    private val altitudeRadiansHorizon = -0.01f // 0 - (diameter of sun)

    /**
     * calculates the civil twilight bases on time and geo-coordinates.
     *
     * @param date date for which to calculate info.
     * @param latitude latitude in degrees.
     * @param longitude latitude in degrees.
     */
    fun calculateSkylightInfo(date: Date, latitude: Double, longitude: Double): SkylightDay {
        val time = date.time
        val daysSince2000 = (time - unixNoonUtc2000).toFloat() / dayInMillis

        val meanAnomaly = calculateMeanAnomaly(daysSince2000)
        val trueAnomaly = calculateTrueAnomaly(meanAnomaly)

        // ecliptic longitude
        val solarLongitude = trueAnomaly + 1.796593063 + Math.PI

        // solar transit in days since 2000
        val arcLongitude = -longitude / 360
        val n = Math.round(daysSince2000 - solarTransitCorrection - arcLongitude).toFloat()
        val solarTransitJ2000 = n + solarTransitCorrection + arcLongitude +
                0.0053 * Math.sin(meanAnomaly.toDouble()) +
                -0.0069 * Math.sin(2 * solarLongitude)

        // declination of sun
        val solarDec = Math.asin(Math.sin(solarLongitude) * Math.sin(obliquity.toDouble()))

        val latitudeRadians = latitude * degreesToRadians

        val cosHourAngleTwilight = calculateCosineHourAngle(altitudeRadiansCivilTwilight, latitudeRadians, solarDec)
        val cosHourAngleHorizon = calculateCosineHourAngle(altitudeRadiansHorizon, latitudeRadians, solarDec)

        return when {
            isAlwaysNight(cosHourAngleTwilight) -> return SkylightDay.NeverLight
            isAlwaysDay(cosHourAngleHorizon) -> return SkylightDay.AlwaysDaytime
            else -> {
                val hourAngleTwilight = (Math.acos(cosHourAngleTwilight) / (2 * Math.PI)).toFloat()
                val dawn = calculateMorningEventUnixTime(solarTransitJ2000, hourAngleTwilight)
                val dusk = calculateEveningEventUnixTime(solarTransitJ2000, hourAngleTwilight)

                val hourAngleHorizon = (Math.acos(cosHourAngleHorizon) / (2 * Math.PI)).toFloat()
                val sunrise = calculateMorningEventUnixTime(solarTransitJ2000, hourAngleHorizon)
                val sunset = calculateEveningEventUnixTime(solarTransitJ2000, hourAngleHorizon)

                when {
                    isAlwaysDay(cosHourAngleTwilight) -> SkylightDay.AlwaysLight(Date(sunrise), Date(sunset))
                    isAlwaysNight(cosHourAngleHorizon) -> SkylightDay.NeverDaytime(Date(dawn), Date(dusk))
                    else -> SkylightDay.Typical(Date(dawn), Date(sunrise), Date(sunset), Date(dusk))
                }
            }
        }

    }

    private fun calculateMeanAnomaly(daysSince2000: Float) = 6.240059968f + daysSince2000 * 0.01720197f

    private fun calculateTrueAnomaly(meanAnomaly: Float) =
        meanAnomaly +
                c1 * Math.sin(meanAnomaly.toDouble()) +
                c2 * Math.sin((2.0 * meanAnomaly)) +
                c3 * Math.sin((3.0 * meanAnomaly))

    private fun calculateCosineHourAngle(altitudeCorrection: Float, latRad: Double, solarDec: Double) =
        (Math.sin(altitudeCorrection.toDouble()) - Math.sin(latRad) * Math.sin(solarDec)) /
                (Math.cos(latRad) * Math.cos(solarDec))

    private fun isAlwaysDay(cosHourAngle: Double) = cosHourAngle <= -1

    private fun isAlwaysNight(cosHourAngle: Double) = cosHourAngle >= 1

    private fun calculateMorningEventUnixTime(solarTransitJ2000: Double, hourAngle: Float) =
        Math.round((solarTransitJ2000 - hourAngle) * dayInMillis) + unixNoonUtc2000

    private fun calculateEveningEventUnixTime(solarTransitJ2000: Double, hourAngle: Float) =
        calculateMorningEventUnixTime(solarTransitJ2000, -hourAngle)
}
