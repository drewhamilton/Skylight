package drewhamilton.skylight.sample

import dagger.Component
import drewhamilton.skylight.sample.main.MainActivity
import drewhamilton.skylight.sso.inject.SsoNetworkModule
import drewhamilton.skylight.sso.inject.SsoSkylightModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    SsoNetworkModule::class,
    SsoSkylightModule::class
])
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    companion object {
        lateinit var instance: AppComponent
    }
}
