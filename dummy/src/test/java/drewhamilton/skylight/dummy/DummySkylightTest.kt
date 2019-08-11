package drewhamilton.skylight.dummy

import drewhamilton.skylight.Coordinates
import drewhamilton.skylight.SkylightDay
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.ZonedDateTime

class DummySkylightTest {

    private val testSkylightDay = SkylightDay.Typical(
        ZonedDateTime.of(1970, 1, 1, 1, 0, 0, 0, ZoneOffset.UTC),
        ZonedDateTime.of(1970, 1, 1, 2, 0, 0, 0, ZoneOffset.UTC),
        ZonedDateTime.of(1970, 1, 1, 3, 0, 0, 0, ZoneOffset.UTC),
        ZonedDateTime.of(1970, 1, 1, 4, 0, 0, 0, ZoneOffset.UTC)
    )

    private lateinit var dummySkylight: DummySkylight

    @Before
    fun setUp() {
        dummySkylight = DummySkylight(testSkylightDay)
    }

    @Test
    fun `getSkylightInfo(Coordinates, LocalDate) returns copy of SkylightDay from constructor with changed date`() {
        val result1 = dummySkylight.getSkylightDay(Coordinates(0.0, 0.0), LocalDate.MIN)
        assertTrue(result1 is SkylightDay.Typical)
        result1 as SkylightDay.Typical
        assertEquals(LocalDate.MIN, result1.dawn.toLocalDate())
        assertEquals(testSkylightDay.dawn.toLocalTime(), result1.dawn.toLocalTime())
        assertEquals(testSkylightDay.dawn.offset, result1.dawn.offset)
        assertEquals(testSkylightDay.sunrise.toLocalTime(), result1.sunrise.toLocalTime())
        assertEquals(testSkylightDay.sunrise.offset, result1.sunrise.offset)
        assertEquals(testSkylightDay.sunset.toLocalTime(), result1.sunset.toLocalTime())
        assertEquals(testSkylightDay.sunset.offset, result1.sunset.offset)
        assertEquals(testSkylightDay.dusk.toLocalTime(), result1.dusk.toLocalTime())
        assertEquals(testSkylightDay.dusk.offset, result1.dusk.offset)

        val result2 = dummySkylight.getSkylightDay(Coordinates(90.0, 180.0), LocalDate.MAX)
        assertTrue(result2 is SkylightDay.Typical)
        result2 as SkylightDay.Typical
        assertEquals(LocalDate.MAX, result2.dawn.toLocalDate())
        assertEquals(testSkylightDay.dawn.toLocalTime(), result2.dawn.toLocalTime())
        assertEquals(testSkylightDay.dawn.offset, result2.dawn.offset)
        assertEquals(testSkylightDay.sunrise.toLocalTime(), result2.sunrise.toLocalTime())
        assertEquals(testSkylightDay.sunrise.offset, result2.sunrise.offset)
        assertEquals(testSkylightDay.sunset.toLocalTime(), result2.sunset.toLocalTime())
        assertEquals(testSkylightDay.sunset.offset, result2.sunset.offset)
        assertEquals(testSkylightDay.dusk.toLocalTime(), result2.dusk.toLocalTime())
        assertEquals(testSkylightDay.dusk.offset, result2.dusk.offset)
    }

    @Test
    fun `getSkylightInfo(LocalDate) returns copy of SkylightDay from constructor with changed date`() {
        val testDate = LocalDate.ofEpochDay(6343)
        val result = dummySkylight.getSkylightDay(testDate)
        assertTrue(result is SkylightDay.Typical)
        result as SkylightDay.Typical
        assertEquals(testDate, result.dawn.toLocalDate())
        assertEquals(testSkylightDay.dawn.toLocalTime(), result.dawn.toLocalTime())
        assertEquals(testSkylightDay.dawn.offset, result.dawn.offset)
        assertEquals(testSkylightDay.sunrise.toLocalTime(), result.sunrise.toLocalTime())
        assertEquals(testSkylightDay.sunrise.offset, result.sunrise.offset)
        assertEquals(testSkylightDay.sunset.toLocalTime(), result.sunset.toLocalTime())
        assertEquals(testSkylightDay.sunset.offset, result.sunset.offset)
        assertEquals(testSkylightDay.dusk.toLocalTime(), result.dusk.toLocalTime())
        assertEquals(testSkylightDay.dusk.offset, result.dusk.offset)
    }
}
