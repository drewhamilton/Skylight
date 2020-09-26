package dev.drewhamilton.skylight.sunrise_sunset_org.network

import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SunriseSunsetOrgDate
internal fun LocalDate.toSunriseSunsetOrgDateString() = DATE_FORMATTER.format(this)

private val DATE_FORMATTER: DateTimeFormatter by lazy { DateTimeFormatter.ISO_LOCAL_DATE }
