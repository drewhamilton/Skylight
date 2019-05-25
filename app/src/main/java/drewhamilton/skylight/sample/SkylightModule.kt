package drewhamilton.skylight.sample

import dagger.Module
import dagger.Provides
import dagger.Reusable
import drewhamilton.skylight.backport.SkylightBackport
import drewhamilton.skylight.backport.calculator.CalculatorSkylight
import drewhamilton.skylight.backport.dummy.DummySkylightBackport
import drewhamilton.skylight.backport.dummy.dagger.DummySkylightBackportComponent
import drewhamilton.skylight.backport.sso.SsoSkylightBackport
import drewhamilton.skylight.backport.sso.dagger.SsoSkylightBackportComponent
import drewhamilton.skylight.sample.source.SkylightRepository

@Module
object SkylightModule {

    @JvmStatic
    @Provides
    @Reusable
    fun ssoSkylight(ssoSkylightComponent: SsoSkylightBackportComponent) = ssoSkylightComponent.skylight()

    @JvmStatic
    @Provides
    @Reusable
    fun dummySkylight(dummySkylightComponent: DummySkylightBackportComponent) = dummySkylightComponent.skylight()

    @JvmStatic
    @Provides
    fun skylight(
        skylightRepository: SkylightRepository,
        ssoSkylight: SsoSkylightBackport,
        calculatorSkylight: CalculatorSkylight,
        dummySkylight: DummySkylightBackport
    ): SkylightBackport = when (skylightRepository.getSelectedSkylightType().blockingGet()!!) {
        SkylightRepository.SkylightType.SSO -> ssoSkylight
        SkylightRepository.SkylightType.CALCULATOR -> calculatorSkylight
        SkylightRepository.SkylightType.DUMMY -> dummySkylight
    }
}
