package drewhamilton.skylight.sample

import dagger.Component
import drewhamilton.skylight.sso.inject.SsoNetworkModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    SsoNetworkModule::class
])
interface AppComponent : ActivityInjector
