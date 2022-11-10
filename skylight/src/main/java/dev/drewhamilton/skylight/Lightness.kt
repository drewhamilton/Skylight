@file:JvmName("SkylightLightness")
package dev.drewhamilton.skylight

import java.time.Instant
import java.time.ZoneOffset

//region Skylight
/**
 * Determine whether it is light outside at the given [coordinates] at the given [instant], where "light" means after
 * dawn and before dusk.
 *
 * To determine whether an instant is after sunrise and before sunset, see [isDaytime].
 */
suspend fun Skylight.isLight(coordinates: Coordinates, instant: Instant): Boolean =
    when (val skylightDay = getSkylightDay(coordinates, instant.atOffset(ZoneOffset.UTC).toLocalDate())) {
        is SkylightDay.AlwaysDaytime -> true
        is SkylightDay.NeverLight -> false
        is SkylightDay.Typical -> skylightDay.isLightAt(instant)
    }

/**
 * Determine whether it is dark outside at the given [coordinates] at the given [instant], where "dark" means before
 * dawn or after dusk.
 *
 * To determine an instant's relationship to sunrise and sunset, see [isDaytime].
 */
suspend fun Skylight.isDark(coordinates: Coordinates, instant: Instant): Boolean =
    !isLight(coordinates, instant)

/**
 * Determine whether it is daytime at the given [coordinates] at the given [instant], where "daytime" means after
 * sunrise and before sunset.
 *
 * To determine an instant's relationship to dawn and dusk, see [isLight] and [isDark].
 */
suspend fun Skylight.isDaytime(coordinates: Coordinates, instant: Instant): Boolean =
    when (val skylightDay = getSkylightDay(coordinates, instant.atOffset(ZoneOffset.UTC).toLocalDate())) {
        is SkylightDay.AlwaysDaytime -> true
        is SkylightDay.NeverLight -> false
        is SkylightDay.Typical -> skylightDay.isDaytimeAt(instant)
    }
//endregion

//region SkylightForCoordinates
/**
 * Determine whether it is light outside at the given [instant], where "light" means after dawn and before dusk.
 */
suspend fun SkylightForCoordinates.isLight(instant: Instant): Boolean =
    skylight.isLight(coordinates, instant)

/**
 * Determine whether it is dark outside at the given [instant], where "dark" means before dawn or after dusk.
 */
suspend fun SkylightForCoordinates.isDark(instant: Instant): Boolean =
    skylight.isDark(coordinates, instant)
//endregion

private fun SkylightDay.Typical.isLightAt(instant: Instant) =
    dawn == null || (dawn.isBefore(instant) && (dusk == null || dusk.isAfter(instant)))

private fun SkylightDay.Typical.isDaytimeAt(instant: Instant) =
    sunrise != null && (sunrise.isBefore(instant) && (sunset == null || sunset.isAfter(instant)))
