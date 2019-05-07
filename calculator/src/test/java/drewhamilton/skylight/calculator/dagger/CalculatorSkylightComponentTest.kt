package drewhamilton.skylight.calculator.dagger

import drewhamilton.skylight.SkylightDay
import drewhamilton.skylight.calculator.TestCoordinates
import drewhamilton.skylight.calculator.TestDateTimes
import drewhamilton.skylight.calculator.asDate
import org.junit.Assert.assertEquals
import org.junit.Test

class CalculatorSkylightComponentTest {

    @Test
    fun `create returns DummySkylightComponent with dummySkylightDay`() {
        val calculatorSkylightComponent = CalculatorSkylightComponent.create()
        val skylight = calculatorSkylightComponent.skylight()

        val skylightDayInSvalbardOnJan6 =
            skylight.getSkylightDay(TestCoordinates.SVALBARD, TestDateTimes.JANUARY_6_2019_NOON.asDate())
        assertEquals(SkylightDay.NeverLight, skylightDayInSvalbardOnJan6)
    }
}
