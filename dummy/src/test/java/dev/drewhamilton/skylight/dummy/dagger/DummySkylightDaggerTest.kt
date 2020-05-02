package dev.drewhamilton.skylight.dummy.dagger

import dev.drewhamilton.skylight.SkylightDay
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class DummySkylightDaggerTest {

    private val testDate = LocalDate.ofEpochDay(4)
    private val testSkylightDay = SkylightDay.NeverLight { date = testDate }

    @Test fun `Dagger provides DummySkylight instance given DummySkylightDay`() {
        val skylight = DummySkylightComponent.create(testSkylightDay).skylight
        assertEquals(testSkylightDay, skylight.getSkylightDay(testDate))
    }
}
