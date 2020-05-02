package dev.drewhamilton.skylight.calculator.dagger

import dev.drewhamilton.skylight.calculator.CalculatorSkylight
import org.junit.Test

class CalculatorSkylightDaggerTest {

    @Test fun `Dagger provides CalculatorSkylight instance`() {
        @Suppress("UNUSED_VARIABLE")
        val skylight: CalculatorSkylight = CalculatorSkylightComponent.create().skylight
    }
}
