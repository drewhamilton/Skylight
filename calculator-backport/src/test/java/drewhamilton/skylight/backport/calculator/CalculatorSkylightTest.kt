package drewhamilton.skylight.backport.calculator

import drewhamilton.skylight.backport.Coordinates
import drewhamilton.skylight.backport.SkylightDay
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetTime
import org.threeten.bp.ZoneOffset

class CalculatorSkylightTest {

    private val skylight = CalculatorSkylight()

    //region Amsterdam on January 6, 2019
    @Test
    fun `Amsterdam on January 6, 2019 is typical`() {
        val result = skylight.getSkylightDay(AMSTERDAM, JANUARY_6_2019)
        assertTrue(result is SkylightDay.Typical)
        result as SkylightDay.Typical
        assertEquals(1546758590702.asEpochMilliToExpectedOffsetTime(), result.dawn)
        assertEquals(1546761159554.asEpochMilliToExpectedOffsetTime(), result.sunrise)
        assertEquals(1546789298271.asEpochMilliToExpectedOffsetTime(), result.sunset)
        assertEquals(1546791867123.asEpochMilliToExpectedOffsetTime(), result.dusk)
    }
    //endregion

    //region Svalbard on January 6, 2019
    @Test
    fun `Svalbard on January 6, 2019 is never light`() {
        val result = skylight.getSkylightDay(SVALBARD, JANUARY_6_2019)
        assertEquals(SkylightDay.NeverLight(JANUARY_6_2019), result)
    }
    //endregion

    private fun Long.asEpochMilliToExpectedOffsetTime() =
        OffsetTime.ofInstant(Instant.ofEpochMilli(this), ZoneOffset.UTC)

    companion object {
        val AMSTERDAM = Coordinates(52.3680, 4.9036)
        val SVALBARD = Coordinates(77.8750, 20.9752)

        val JANUARY_6_2019: LocalDate = LocalDate.parse("2019-01-06")
    }
}
