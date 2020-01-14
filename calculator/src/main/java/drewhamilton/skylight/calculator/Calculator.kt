package drewhamilton.skylight.calculator

internal fun calculateSkylightInfo(
    epochMillis: Long,
    latitude: Double,
    longitude: Double,
    // This is the same altitude TwilightCalculator uses for civil twilight:
    altitudeRadiansCivilTwilight: Float = -0.104719755f,
    // Approximately equal to -{diameter of sun}
    altitudeRadiansHorizon: Float = -0.01f
): EpochMilliSkylightDay {
    val daysSince2000 = (epochMillis - UNIX_NOON_UTC_2000).toFloat() / DAY_IN_MILLIS

    val meanAnomaly = calculateMeanAnomaly(daysSince2000)
    val trueAnomaly = calculateTrueAnomaly(meanAnomaly)

    // ecliptic longitude
    val solarLongitude = trueAnomaly + 1.796593063 + Math.PI

    // solar transit in days since 2000
    val arcLongitude = -longitude / 360
    val n = Math.round(daysSince2000 - CORRECTION_SOLAR_TRANSIT - arcLongitude).toFloat()
    val solarTransitJ2000 = n + CORRECTION_SOLAR_TRANSIT + arcLongitude +
            0.0053 * Math.sin(meanAnomaly.toDouble()) +
            -0.0069 * Math.sin(2 * solarLongitude)

    // declination of sun
    val solarDec = Math.asin(Math.sin(solarLongitude) * Math.sin(OBLIQUITY.toDouble()))

    val latitudeRadians = latitude * DEGREES_TO_RADIANS

    val cosHourAngleTwilight = calculateCosineHourAngle(altitudeRadiansCivilTwilight, latitudeRadians, solarDec)
    val cosHourAngleHorizon = calculateCosineHourAngle(altitudeRadiansHorizon, latitudeRadians, solarDec)

    return when {
        isAlwaysNight(cosHourAngleTwilight) -> EpochMilliSkylightDay.NeverLight
        isAlwaysDay(cosHourAngleHorizon) -> EpochMilliSkylightDay.AlwaysDaytime
        else -> {
            val hourAngleTwilight = (Math.acos(cosHourAngleTwilight) / (2 * Math.PI)).toFloat()
            val dawn = calculateMorningEventUnixTime(solarTransitJ2000, hourAngleTwilight)
            val dusk = calculateEveningEventUnixTime(solarTransitJ2000, hourAngleTwilight)

            val hourAngleHorizon = (Math.acos(cosHourAngleHorizon) / (2 * Math.PI)).toFloat()
            val sunrise = calculateMorningEventUnixTime(solarTransitJ2000, hourAngleHorizon)
            val sunset = calculateEveningEventUnixTime(solarTransitJ2000, hourAngleHorizon)

            when {
                isAlwaysDay(cosHourAngleTwilight) -> EpochMilliSkylightDay.AlwaysLight(sunrise, sunset)
                isAlwaysNight(cosHourAngleHorizon) -> EpochMilliSkylightDay.NeverDaytime(dawn, dusk)
                else -> EpochMilliSkylightDay.Typical(dawn, sunrise, sunset, dusk)
            }
        }
    }
}

private fun calculateMeanAnomaly(daysSince2000: Float) = 6.240059968f + daysSince2000 * 0.01720197f

private fun calculateTrueAnomaly(meanAnomaly: Float) =
    meanAnomaly +
            C1 * Math.sin(meanAnomaly.toDouble()) +
            C2 * Math.sin((2.0 * meanAnomaly)) +
            C3 * Math.sin((3.0 * meanAnomaly))

private fun calculateCosineHourAngle(altitudeCorrection: Float, latRad: Double, solarDec: Double) =
    (Math.sin(altitudeCorrection.toDouble()) - Math.sin(latRad) * Math.sin(solarDec)) /
            (Math.cos(latRad) * Math.cos(solarDec))

private fun isAlwaysDay(cosHourAngle: Double) = cosHourAngle <= -1

private fun isAlwaysNight(cosHourAngle: Double) = cosHourAngle >= 1

private fun calculateMorningEventUnixTime(solarTransitJ2000: Double, hourAngle: Float) =
    Math.round((solarTransitJ2000 - hourAngle) * DAY_IN_MILLIS) + UNIX_NOON_UTC_2000

private fun calculateEveningEventUnixTime(solarTransitJ2000: Double, hourAngle: Float) =
    calculateMorningEventUnixTime(solarTransitJ2000, -hourAngle)

private const val DEGREES_TO_RADIANS = (Math.PI / 180.0f).toFloat()
private const val DAY_IN_MILLIS = 1000 * 60 * 60 * 24

// Java time on Jan 1, 2000 12:00 UTC.
private const val UNIX_NOON_UTC_2000 = 946728000000L

private const val CORRECTION_SOLAR_TRANSIT = 0.0009
private const val C1 = 0.0334196f
private const val C2 = 0.000349066f
private const val C3 = 0.000005236f
private const val OBLIQUITY = 0.40927971f
