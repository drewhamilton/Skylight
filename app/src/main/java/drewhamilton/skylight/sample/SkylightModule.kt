package drewhamilton.skylight.sample

import dagger.Module
import dagger.Provides
import dagger.Reusable
import drewhamilton.skylight.Skylight
import drewhamilton.skylight.calculator.CalculatorSkylight
import drewhamilton.skylight.dummy.DummySkylight
import drewhamilton.skylight.dummy.dagger.DummySkylightComponent
import drewhamilton.skylight.sample.source.SkylightRepository
import drewhamilton.skylight.sso.SsoSkylight
import drewhamilton.skylight.sso.dagger.SsoSkylightComponent

@Module
object SkylightModule {

    @JvmStatic
    @Provides
    @Reusable
    fun ssoSkylight(ssoSkylightComponent: SsoSkylightComponent) = ssoSkylightComponent.skylight()

    @JvmStatic
    @Provides
    @Reusable
    fun dummySkylight(dummySkylightComponent: DummySkylightComponent) = dummySkylightComponent.skylight()

    @JvmStatic
    @Provides
    fun skylight(
        skylightRepository: SkylightRepository,
        ssoSkylight: SsoSkylight,
        calculatorSkylight: CalculatorSkylight,
        dummySkylight: DummySkylight
    ): Skylight = when (skylightRepository.getSelectedSkylightType().blockingGet()!!) {
        SkylightRepository.SkylightType.SSO -> ssoSkylight
        SkylightRepository.SkylightType.CALCULATOR -> calculatorSkylight
        SkylightRepository.SkylightType.DUMMY -> dummySkylight
    }
}
