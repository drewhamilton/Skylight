package drewhamilton.skylight.sso.dagger

import dagger.BindsInstance
import dagger.Component
import drewhamilton.skylight.sso.SsoSkylight
import okhttp3.OkHttpClient

/**
 * A Dagger component providing an instance of [SsoSkylight].
 */
@Component(modules = [SsoSkylightModule::class])
interface SsoSkylightComponent {

    fun skylight(): SsoSkylight

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance okHttpClient: OkHttpClient): SsoSkylightComponent
    }

    companion object {
        fun create(okHttpClient: OkHttpClient = OkHttpClient()) =
            DaggerSsoSkylightComponent.factory().create(okHttpClient)
    }
}
