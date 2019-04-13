package drewhamilton.skylight.dummy

import drewhamilton.skylight.Coordinates
import drewhamilton.skylight.SkylightDay
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Date

class DummySkylightTest {

    private val testSkylightDay: SkylightDay = SkylightDay.Typical(Date(1), Date(2), Date(3), Date(4))

    private lateinit var dummySkylight: DummySkylight

    @Before
    fun setUp() {
        dummySkylight = DummySkylight(testSkylightDay)
    }

    @Test
    fun `getSkylightInfo returns SkylightDay from constructor`() {
        val result1 = dummySkylight.getSkylightDay(Coordinates(0.0, 0.0), Date(1))
        assertEquals(testSkylightDay, result1)

        val result2 = dummySkylight.getSkylightDay(Coordinates(90.0, 180.0), Date(999999999999999))
        assertEquals(testSkylightDay, result2)
    }

    @Test
    fun `skylightDay property equals value from constructor`() {
        assertEquals(testSkylightDay, dummySkylight.skylightDay)
    }
}
