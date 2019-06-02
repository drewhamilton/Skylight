package drewhamilton.skylight.backport.dummy

import drewhamilton.skylight.backport.Coordinates
import drewhamilton.skylight.backport.SkylightDay
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetTime
import org.threeten.bp.ZoneOffset

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
        Assert.assertEquals(LocalDate.MIN, result1.date)
        Assert.assertTrue(result1 is SkylightDay.Typical)
        result1 as SkylightDay.Typical
        Assert.assertEquals(testSkylightDay.dawn, result1.dawn)
        Assert.assertEquals(testSkylightDay.sunrise, result1.sunrise)
        Assert.assertEquals(testSkylightDay.sunset, result1.sunset)
        Assert.assertEquals(testSkylightDay.dawn, result1.dawn)

        val result2 = dummySkylight.getSkylightDay(Coordinates(90.0, 180.0), LocalDate.MAX)
        Assert.assertEquals(LocalDate.MAX, result2.date)
        Assert.assertTrue(result2 is SkylightDay.Typical)
        result2 as SkylightDay.Typical
        Assert.assertEquals(testSkylightDay.dawn, result2.dawn)
        Assert.assertEquals(testSkylightDay.sunrise, result2.sunrise)
        Assert.assertEquals(testSkylightDay.sunset, result2.sunset)
        Assert.assertEquals(testSkylightDay.dawn, result2.dawn)
    }

    @Test fun `getSkylightInfo(LocalDate) returns copy of SkylightDay from constructor with changed date`() {
        val testDate = LocalDate.ofEpochDay(6343)
        val result = dummySkylight.getSkylightDay(testDate)
        Assert.assertEquals(testDate, result.date)
        Assert.assertTrue(result is SkylightDay.Typical)
        result as SkylightDay.Typical
        Assert.assertEquals(testSkylightDay.dawn, result.dawn)
        Assert.assertEquals(testSkylightDay.sunrise, result.sunrise)
        Assert.assertEquals(testSkylightDay.sunset, result.sunset)
        Assert.assertEquals(testSkylightDay.dawn, result.dawn)
    }
}
