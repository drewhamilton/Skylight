package dev.drewhamilton.skylight.dummy

import dev.drewhamilton.skylight.Coordinates
import dev.drewhamilton.skylight.SkylightDay
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.ZonedDateTime

class DummySkylightTest {

    private val testSkylightDay = SkylightDay.Typical {
        date = LocalDate.of(1970, 1, 1)
        dawn = ZonedDateTime.of(1970, 1, 1, 8, 0, 0, 0, ZoneOffset.UTC)
        sunrise = ZonedDateTime.of(1970, 1, 1, 9, 0, 0, 0, ZoneOffset.UTC)
        sunset = ZonedDateTime.of(1970, 1, 1, 10, 0, 0, 0, ZoneOffset.UTC)
        dusk = ZonedDateTime.of(1970, 1, 1, 11, 0, 0, 0, ZoneOffset.UTC)
    }

    private lateinit var dummySkylight: DummySkylight

    @Before
    fun setUp() {
        dummySkylight = DummySkylight(testSkylightDay)
    }

    @Test
    fun `getSkylightInfo(Coordinates, LocalDate, ZoneId) returns copy of dummySkylightDay with changed date`() {
        val offset = ZoneOffset.ofHours(2)

        val result1 = dummySkylight.getSkylightDay(Coordinates(0.0, 0.0), LocalDate.MIN, offset)
        assertTrue(result1 is SkylightDay.Typical)
        result1 as SkylightDay.Typical
        assertEquals(LocalDate.MIN, result1.dawn?.toLocalDate())
        assertEquals(testSkylightDay.dawn!!.toLocalTime(offset), result1.dawn?.toLocalTime())
        assertEquals(offset, result1.dawn?.offset)
        assertEquals(testSkylightDay.sunrise!!.toLocalTime(offset), result1.sunrise?.toLocalTime())
        assertEquals(offset, result1.sunrise?.offset)
        assertEquals(testSkylightDay.sunset!!.toLocalTime(offset), result1.sunset?.toLocalTime())
        assertEquals(offset, result1.sunset?.offset)
        assertEquals(testSkylightDay.dusk!!.toLocalTime(offset), result1.dusk?.toLocalTime())
        assertEquals(offset, result1.dusk?.offset)

        val result2 = dummySkylight.getSkylightDay(Coordinates(90.0, 180.0), LocalDate.MAX, offset)
        assertTrue(result2 is SkylightDay.Typical)
        result2 as SkylightDay.Typical
        assertEquals(LocalDate.MAX, result2.dawn?.toLocalDate())
        assertEquals(testSkylightDay.dawn!!.toLocalTime(offset), result2.dawn?.toLocalTime())
        assertEquals(offset, result2.dawn?.offset)
        assertEquals(testSkylightDay.sunrise!!.toLocalTime(offset), result2.sunrise?.toLocalTime())
        assertEquals(offset, result2.sunrise?.offset)
        assertEquals(testSkylightDay.sunset!!.toLocalTime(offset), result2.sunset?.toLocalTime())
        assertEquals(offset, result2.sunset?.offset)
        assertEquals(testSkylightDay.dusk!!.toLocalTime(offset), result2.dusk?.toLocalTime())
        assertEquals(offset, result2.dusk?.offset)
    }

    @Test
    fun `getSkylightInfo(LocalDate, ZoneId) returns copy of dummySkylightDay with changed date`() {
        val testDate = LocalDate.ofEpochDay(6343)
        val offset = ZoneOffset.ofHours(-7)
        val result = dummySkylight.getSkylightDay(testDate, offset)
        assertTrue(result is SkylightDay.Typical)
        result as SkylightDay.Typical
        assertEquals(testDate, result.dawn?.toLocalDate())
        assertEquals(testSkylightDay.dawn!!.toLocalTime(offset), result.dawn?.toLocalTime())
        assertEquals(offset, result.dawn?.offset)
        assertEquals(testSkylightDay.sunrise!!.toLocalTime(offset), result.sunrise?.toLocalTime())
        assertEquals(offset, result.sunrise?.offset)
        assertEquals(testSkylightDay.sunset!!.toLocalTime(offset), result.sunset?.toLocalTime())
        assertEquals(offset, result.sunset?.offset)
        assertEquals(testSkylightDay.dusk!!.toLocalTime(offset), result.dusk?.toLocalTime())
        assertEquals(offset, result.dusk?.offset)
    }

    private fun ZonedDateTime.toLocalTime(offset: ZoneOffset) = withZoneSameInstant(offset).toLocalTime()
}
