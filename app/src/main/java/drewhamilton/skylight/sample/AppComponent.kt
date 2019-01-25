package drewhamilton.skylight.sample

import dagger.Component
import drewhamilton.skylight.sample.main.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    SkylightModule::class
])
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    companion object {
        lateinit var instance: AppComponent
    }
}
