package drewhamilton.skylight.backport.dummy.dagger

import drewhamilton.skylight.backport.SkylightDayBackport
import org.junit.Assert.assertEquals
import org.junit.Test
import org.threeten.bp.LocalDate

class DummySkylightBackportComponentTest {

    private val testSkylightDay = SkylightDayBackport.NeverLight(LocalDate.ofEpochDay(4))

    @Test
    fun `create returns DummySkylightBackportComponent with dummySkylightDay`() {
        val dummySkylightBackportComponent = DummySkylightBackportComponent.create(testSkylightDay)
        val skylight = dummySkylightBackportComponent.skylight()

        assertEquals(testSkylightDay, skylight.getSkylightDay())
    }
}
