package drewhamilton.skylight.dummy.dagger

import drewhamilton.skylight.SkylightDay
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class DummySkylightComponentTest {

    private val testSkylightDay = SkylightDay.NeverLight(LocalDate.ofEpochDay(4))

    @Test
    fun `create returns DummySkylightComponent with dummySkylightDay`() {
        val dummySkylightComponent = DummySkylightComponent.create(testSkylightDay)
        val skylight = dummySkylightComponent.skylight()

        assertEquals(testSkylightDay, skylight.getSkylightDay())
    }
}
