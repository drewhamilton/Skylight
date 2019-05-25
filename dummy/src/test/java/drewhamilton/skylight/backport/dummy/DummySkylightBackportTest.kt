package drewhamilton.skylight.backport.dummy

import drewhamilton.skylight.backport.CoordinatesBackport
import drewhamilton.skylight.backport.SkylightDayBackport
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetTime
import org.threeten.bp.ZoneOffset

class DummySkylightBackportTest {

    private val testSkylightDay = SkylightDayBackport.Typical(
        LocalDate.ofEpochDay(1),
        OffsetTime.of(1, 0, 0, 0, ZoneOffset.UTC),
        OffsetTime.of(2, 0, 0, 0, ZoneOffset.UTC),
        OffsetTime.of(3, 0, 0, 0, ZoneOffset.UTC),
        OffsetTime.of(4, 0, 0, 0, ZoneOffset.UTC)
    )

    private lateinit var dummySkylight: DummySkylightBackport

    @Before
    fun setUp() {
        dummySkylight = DummySkylightBackport(testSkylightDay)
    }

    @Test
    fun `getSkylightInfo returns SkylightDay from constructor`() {
        val result1 = dummySkylight.getSkylightDay(CoordinatesBackport(0.0, 0.0), LocalDate.MIN)
        Assert.assertEquals(testSkylightDay, result1)

        val result2 = dummySkylight.getSkylightDay(CoordinatesBackport(90.0, 180.0), LocalDate.MAX)
        Assert.assertEquals(testSkylightDay, result2)
    }

    @Test
    fun `skylightDay property equals value from constructor`() {
        Assert.assertEquals(testSkylightDay, dummySkylight.getSkylightDay())
    }
}
