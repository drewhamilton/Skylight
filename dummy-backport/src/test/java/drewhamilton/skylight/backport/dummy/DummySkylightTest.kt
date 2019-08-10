package drewhamilton.skylight.backport.dummy

import drewhamilton.skylight.backport.Coordinates
import drewhamilton.skylight.backport.SkylightDay
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

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
        Assert.assertTrue(result1 is SkylightDay.Typical)
        result1 as SkylightDay.Typical
        Assert.assertEquals(LocalDate.MIN, result1.dawn.toLocalDate())
        Assert.assertEquals(testSkylightDay.dawn.toLocalTime(), result1.dawn.toLocalTime())
        Assert.assertEquals(testSkylightDay.dawn.offset, result1.dawn.offset)
        Assert.assertEquals(testSkylightDay.sunrise.toLocalTime(), result1.sunrise.toLocalTime())
        Assert.assertEquals(testSkylightDay.sunrise.offset, result1.sunrise.offset)
        Assert.assertEquals(testSkylightDay.sunset.toLocalTime(), result1.sunset.toLocalTime())
        Assert.assertEquals(testSkylightDay.sunset.offset, result1.sunset.offset)
        Assert.assertEquals(testSkylightDay.dusk.toLocalTime(), result1.dusk.toLocalTime())
        Assert.assertEquals(testSkylightDay.dusk.offset, result1.dusk.offset)

        val result2 = dummySkylight.getSkylightDay(Coordinates(90.0, 180.0), LocalDate.MAX)
        Assert.assertTrue(result2 is SkylightDay.Typical)
        result2 as SkylightDay.Typical
        Assert.assertEquals(LocalDate.MAX, result2.dawn.toLocalDate())
        Assert.assertEquals(testSkylightDay.dawn.toLocalTime(), result2.dawn.toLocalTime())
        Assert.assertEquals(testSkylightDay.dawn.offset, result2.dawn.offset)
        Assert.assertEquals(testSkylightDay.sunrise.toLocalTime(), result2.sunrise.toLocalTime())
        Assert.assertEquals(testSkylightDay.sunrise.offset, result2.sunrise.offset)
        Assert.assertEquals(testSkylightDay.sunset.toLocalTime(), result2.sunset.toLocalTime())
        Assert.assertEquals(testSkylightDay.sunset.offset, result2.sunset.offset)
        Assert.assertEquals(testSkylightDay.dusk.toLocalTime(), result2.dusk.toLocalTime())
        Assert.assertEquals(testSkylightDay.dusk.offset, result2.dusk.offset)
    }

    @Test fun `getSkylightInfo(LocalDate) returns copy of SkylightDay from constructor with changed date`() {
        val testDate = LocalDate.ofEpochDay(6343)
        val result = dummySkylight.getSkylightDay(testDate)
        Assert.assertTrue(result is SkylightDay.Typical)
        result as SkylightDay.Typical
        Assert.assertEquals(testDate, result.dawn.toLocalDate())
        Assert.assertEquals(testSkylightDay.dawn.toLocalTime(), result.dawn.toLocalTime())
        Assert.assertEquals(testSkylightDay.dawn.offset, result.dawn.offset)
        Assert.assertEquals(testSkylightDay.sunrise.toLocalTime(), result.sunrise.toLocalTime())
        Assert.assertEquals(testSkylightDay.sunrise.offset, result.sunrise.offset)
        Assert.assertEquals(testSkylightDay.sunset.toLocalTime(), result.sunset.toLocalTime())
        Assert.assertEquals(testSkylightDay.sunset.offset, result.sunset.offset)
        Assert.assertEquals(testSkylightDay.dusk.toLocalTime(), result.dusk.toLocalTime())
        Assert.assertEquals(testSkylightDay.dusk.offset, result.dusk.offset)
    }
}
