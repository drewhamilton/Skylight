@file:JvmName("SkylightLightness")
package dev.drewhamilton.skylight

import java.time.Instant
import java.time.ZonedDateTime

//region Skylight
/**
 * Determine whether it is light outside at the given [coordinates] at the given [dateTime], where "light" means after
 * dawn and before dusk on the given date.
 */
fun Skylight.isLight(coordinates: Coordinates, dateTime: ZonedDateTime): Boolean =
    when (val skylightDay = getSkylightDay(coordinates, dateTime.toLocalDate())) {
        is SkylightDay.AlwaysDaytime -> true
        is SkylightDay.NeverLight -> false
        is SkylightDay.Typical -> skylightDay.isLightAt(dateTime.toInstant())
    }

/**
 * Determine whether it is dark outside at the given [coordinates] at the given [dateTime], where "dark" means before
 * dawn or after dusk on the given date.
 */
fun Skylight.isDark(coordinates: Coordinates, dateTime: ZonedDateTime): Boolean = !isLight(coordinates, dateTime)

/**
 * Determine whether it is daytime at the given [coordinates] at the given [dateTime], where "daytime" means after
 * sunrise and before sunset on the given date.
 */
fun Skylight.isDaytime(coordinates: Coordinates, dateTime: ZonedDateTime): Boolean =
    when (val skylightDay = getSkylightDay(coordinates, dateTime.toLocalDate())) {
        is SkylightDay.AlwaysDaytime -> true
        is SkylightDay.NeverLight -> false
        is SkylightDay.Typical -> skylightDay.isDaytimeAt(dateTime.toInstant())
    }
//endregion

//region SkylightForCoordinates
/**
 * @param dateTime The date-time at which to check for lightness.
 * @return Whether it is light outside at the [SkylightForCoordinates]'s coordinates at the given date-time, where
 * "light" means after dawn and before dusk on the given date.
 */
fun SkylightForCoordinates.isLight(dateTime: ZonedDateTime) = skylight.isLight(coordinates, dateTime)

/**
 * @param dateTime The date-time at which to check for darkness.
 * @return Whether it is dark outside at the [SkylightForCoordinates]'s coordinates at the given date-time, where "dark"
 * means before dawn or after dusk on the given date.
 */
fun SkylightForCoordinates.isDark(dateTime: ZonedDateTime) = skylight.isDark(coordinates, dateTime)
//endregion

private fun SkylightDay.Typical.isLightAt(instant: Instant) =
    dawn == null || (dawn.isBefore(instant) && (dusk == null || dusk.isAfter(instant)))

private fun SkylightDay.Typical.isDaytimeAt(instant: Instant) =
    sunrise != null && (sunrise.isBefore(instant) && (sunset == null || sunset.isAfter(instant)))
