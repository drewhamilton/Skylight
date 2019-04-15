package drewhamilton.skylight.dummy.dagger

import drewhamilton.skylight.SkylightDay
import org.junit.Assert.assertEquals
import org.junit.Test

class DummySkylightComponentTest {

    private val testSkylightDay: SkylightDay = SkylightDay.NeverLight

    @Test
    fun `create returns DummySkylightComponent with dummySkylightDay`() {
        val dummySkylightComponent = DummySkylightComponent.create(testSkylightDay)
        val skylight = dummySkylightComponent.skylight()

        assertEquals(testSkylightDay, skylight.getSkylightDay())
    }
}
