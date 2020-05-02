package dev.drewhamilton.skylight.sso.dagger

import dagger.BindsInstance
import dagger.Component
import dev.drewhamilton.skylight.sso.SsoSkylight
import okhttp3.OkHttpClient

/**
 * A Dagger component providing an instance of [SsoSkylight].
 */
@Component(modules = [SsoNetworkModule::class])
interface SsoSkylightComponent {

    fun skylight(): SsoSkylight

    @Component.Factory interface Factory {
        fun create(@BindsInstance okHttpClient: OkHttpClient): SsoSkylightComponent
    }

    companion object {

        /*
         * TODO WORKAROUND: The second function forces consumers to include the OkHttp dependency, even when relying
         *  on the default parameters, even when @JvmOverloads is applied. This method allows consumers to not import
         *  OkHttp if they don't want to.
         */
        fun create() = create(OkHttpClient())

        fun create(okHttpClient: OkHttpClient = OkHttpClient()) =
            DaggerSsoSkylightComponent.factory().create(okHttpClient)
    }
}
