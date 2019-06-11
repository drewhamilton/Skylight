package drewhamilton.skylight.views.event

import org.junit.After
import org.junit.Before
import java.util.TimeZone

/**
 * A base test class that alters the system default time zone to a specified time zone before each test, and restores
 * the previous system default after each test.
 */
abstract class AlteredTimeZoneTest(
    private val testSystemDefaultTimeZone: TimeZone? = TimeZone.getTimeZone("UTC")
) {

    private var systemDefaultTimeZone: TimeZone? = null

    @Before
    fun setDefaultTimeZoneToUtc() {
        systemDefaultTimeZone = TimeZone.getDefault()
        TimeZone.setDefault(testSystemDefaultTimeZone)
    }

    @After
    fun restoreSystemDefaultTimeZone() {
        TimeZone.setDefault(systemDefaultTimeZone)
    }
}
