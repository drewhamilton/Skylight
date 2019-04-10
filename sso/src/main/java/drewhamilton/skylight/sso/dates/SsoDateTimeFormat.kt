package drewhamilton.skylight.sso.dates

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.regex.Pattern

/**
 * Parses and prints dates matching the format: 2015-05-21T19:52:17+02:00
 */
internal class SsoDateTimeFormat(
    private val shouldFormatWithZ: Boolean = false
) : JavaDateFormatWrapper(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US), TimeZone.getTimeZone("UTC")) {

    override fun format(date: Date): String {
        val text = super.format(date)
        return text.formatTimeZoneForExternalUse()
    }

    private fun String.formatTimeZoneForExternalUse(): String {
        // Ends with e.g. "+02:00":
        val textWithTimeZoneColon = insertTimeZoneColon()

        var text = textWithTimeZoneColon
        if (shouldFormatWithZ && textWithTimeZoneColon.contains(EXTERNAL_TIME_ZONE_UTC)) {
            text = text.replace(EXTERNAL_TIME_ZONE_UTC, EXTERNAL_TIME_ZONE_Z)
        }

        // Ends with e.g. "+02:00" for most time zones, or "Z" for UTC:
        return text
    }

    private fun String.insertTimeZoneColon(): String {
        val internalTimeZone = extractPattern(Pattern.compile(PATTERN_INTERNAL_TIME_ZONE))
        val indexOfTimeZoneColon = EXTERNAL_TIME_ZONE_UTC.indexOf(COLON)
        val externalTimeZone = internalTimeZone.insert(COLON.toString(), indexOfTimeZoneColon)
        return replace(internalTimeZone, externalTimeZone)
    }

    private fun String.insert(insertion: String, index: Int) =
        substring(0, index) + insertion + substring(index, length)

    override fun parse(text: String): Date {
        val modifiedText = text.formatTimeZoneForInternalParsing()
        return super.parse(modifiedText)
    }

    private fun String.formatTimeZoneForInternalParsing() =
        if (contains(EXTERNAL_TIME_ZONE_Z))
            replace(EXTERNAL_TIME_ZONE_Z, INTERNAL_TIME_ZONE_UTC)
        else
            removeTimeZoneColon()

    private fun String.removeTimeZoneColon(): String {
        try {
            val externalTimeZone = extractPattern(Pattern.compile(PATTERN_EXTERNAL_TIME_ZONE_FULL))
            val indexOfTimeZoneColon = externalTimeZone.indexOf(COLON)
            val internalTimeZone = externalTimeZone.removeRange(indexOfTimeZoneColon, indexOfTimeZoneColon + 1)
            return replace(externalTimeZone, internalTimeZone)
        } catch (ex: IllegalStateException) {
            throw ParseException("Source string did not contain expected time zone: ${ex.message}", length)
        }
    }

    private fun String.extractPattern(pattern: Pattern): String {
        val matcher = pattern.matcher(this)
        return if (matcher.find()) {
            matcher.group(0)
        } else {
            throw IllegalStateException("Did not find pattern $pattern")
        }
    }

    private companion object {
        private const val COLON = ':'

        private const val PATTERN_EXTERNAL_TIME_ZONE_FULL = "[+-][0-9]{2}:[0-9]{2}"
        private const val PATTERN_INTERNAL_TIME_ZONE = "[+-][0-9]{4}"

        private const val EXTERNAL_TIME_ZONE_Z = "Z"
        private const val EXTERNAL_TIME_ZONE_UTC = "+00:00"
        private const val INTERNAL_TIME_ZONE_UTC = "+0000"
    }
}
