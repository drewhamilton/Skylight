package drewhamilton.skylight.dummy

import drewhamilton.skylight.Coordinates
import drewhamilton.skylight.SkylightDay
import org.junit.Assert.assertEquals
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
    fun `getSkylightInfo returns SkylightDay from constructor`() {
        val result1 = dummySkylight.getSkylightDay(Coordinates(0.0, 0.0), LocalDate.MIN)
        assertEquals(testSkylightDay, result1)

        val result2 = dummySkylight.getSkylightDay(Coordinates(90.0, 180.0), LocalDate.MAX)
        assertEquals(testSkylightDay, result2)
    }

    @Test
    fun `skylightDay property equals value from constructor`() {
        assertEquals(testSkylightDay, dummySkylight.getSkylightDay())
    }
}
