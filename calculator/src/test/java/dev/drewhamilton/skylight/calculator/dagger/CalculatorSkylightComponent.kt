package dev.drewhamilton.skylight.calculator.dagger

import dagger.Component
import dev.drewhamilton.skylight.calculator.CalculatorSkylight

@Component interface CalculatorSkylightComponent {

    val skylight: CalculatorSkylight

    @Component.Factory interface Factory {
        fun create(): CalculatorSkylightComponent
    }

    companion object {
        fun create(): CalculatorSkylightComponent =
            DaggerCalculatorSkylightComponent.factory().create()
    }
}
