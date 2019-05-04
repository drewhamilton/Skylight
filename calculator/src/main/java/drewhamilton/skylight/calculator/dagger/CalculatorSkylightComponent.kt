package drewhamilton.skylight.calculator.dagger

import dagger.Component
import drewhamilton.skylight.calculator.CalculatorSkylight

@Component
interface CalculatorSkylightComponent {

    fun skylight(): CalculatorSkylight

    @Component.Factory
    interface Factory {
        fun create(): CalculatorSkylightComponent
    }

    companion object {
        fun create() = DaggerCalculatorSkylightComponent.factory().create()
    }
}
