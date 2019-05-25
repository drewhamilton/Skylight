package drewhamilton.skylight.sample

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import drewhamilton.skylight.backport.dummy.dagger.DummySkylightComponent
import drewhamilton.skylight.backport.sso.dagger.SsoSkylightComponent
import drewhamilton.skylight.sample.main.MainActivity
import drewhamilton.skylight.sample.settings.SettingsActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    SkylightModule::class
])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(settingsActivity: SettingsActivity)

    @Component.Factory interface Factory {
        fun create(
            @BindsInstance application: Application,
            @BindsInstance ssoSkylightComponent: SsoSkylightComponent,
            @BindsInstance dummySkylightComponent: DummySkylightComponent
        ): AppComponent
    }

    companion object {
        val instance get() = _instance

        @Suppress("ObjectPropertyName")
        private lateinit var _instance: AppComponent

        fun create(
            application: Application,
            ssoSkylightComponent: SsoSkylightComponent,
            dummySkylightComponent: DummySkylightComponent
        ) {
            _instance = DaggerAppComponent.factory().create(application, ssoSkylightComponent, dummySkylightComponent)
        }
    }
}
