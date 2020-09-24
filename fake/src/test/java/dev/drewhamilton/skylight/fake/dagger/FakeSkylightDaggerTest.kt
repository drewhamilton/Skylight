package dev.drewhamilton.skylight.fake.dagger

import dev.drewhamilton.skylight.SkylightDay
import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Test

class FakeSkylightDaggerTest {

    private val testDate = LocalDate.ofEpochDay(4)
    private val testSkylightDay = SkylightDay.NeverLight (date = testDate)

    @Test fun `Dagger provides DummySkylight instance given DummySkylightDay`() {
        val skylight = FakeSkylightComponent.create(testSkylightDay).skylight
        assertEquals(testSkylightDay, skylight.getSkylightDay(testDate))
    }
}
