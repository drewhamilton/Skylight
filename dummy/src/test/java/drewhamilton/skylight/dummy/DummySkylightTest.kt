package drewhamilton.skylight.dummy

import drewhamilton.skylight.Coordinates
import drewhamilton.skylight.SkylightDay
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.OffsetTime
import java.time.ZoneOffset

class DummySkylightTest {

    private val testSkylightDay = SkylightDay.Typical(
        LocalDate.ofEpochDay(1),
        OffsetTime.of(1, 0, 0, 0, ZoneOffset.UTC),
        OffsetTime.of(2, 0, 0, 0, ZoneOffset.UTC),
        OffsetTime.of(3, 0, 0, 0, ZoneOffset.UTC),
        OffsetTime.of(4, 0, 0, 0, ZoneOffset.UTC)
    )

    private lateinit var dummySkylight: DummySkylight

    @Before
    fun setUp() {
        dummySkylight = DummySkylight(testSkylightDay)
    }

    @Test
    fun `getSkylightInfo(Coordinates, LocalDate) returns copy of SkylightDay from constructor with changed date`() {
        val result1 = dummySkylight.getSkylightDay(Coordinates(0.0, 0.0), LocalDate.MIN)
        assertEquals(LocalDate.MIN, result1.date)
        assertTrue(result1 is SkylightDay.Typical)
        result1 as SkylightDay.Typical
        assertEquals(testSkylightDay.dawn, result1.dawn)
        assertEquals(testSkylightDay.sunrise, result1.sunrise)
        assertEquals(testSkylightDay.sunset, result1.sunset)
        assertEquals(testSkylightDay.dawn, result1.dawn)

        val result2 = dummySkylight.getSkylightDay(Coordinates(90.0, 180.0), LocalDate.MAX)
        assertEquals(LocalDate.MAX, result2.date)
        assertTrue(result2 is SkylightDay.Typical)
        result2 as SkylightDay.Typical
        assertEquals(testSkylightDay.dawn, result2.dawn)
        assertEquals(testSkylightDay.sunrise, result2.sunrise)
        assertEquals(testSkylightDay.sunset, result2.sunset)
        assertEquals(testSkylightDay.dawn, result2.dawn)
    }

    @Test fun `getSkylightInfo(LocalDate) returns copy of SkylightDay from constructor with changed date`() {
        val testDate = LocalDate.ofEpochDay(6343)
        val result = dummySkylight.getSkylightDay(testDate)
        assertEquals(testDate, result.date)
        assertTrue(result is SkylightDay.Typical)
        result as SkylightDay.Typical
        assertEquals(testSkylightDay.dawn, result.dawn)
        assertEquals(testSkylightDay.sunrise, result.sunrise)
        assertEquals(testSkylightDay.sunset, result.sunset)
        assertEquals(testSkylightDay.dawn, result.dawn)
    }
}
