package dev.drewhamilton.skylight.sso.dagger

import dagger.BindsInstance
import dagger.Component
import dev.drewhamilton.skylight.sso.SsoSkylight
import okhttp3.OkHttpClient

/**
 * A Dagger component providing an instance of [SsoSkylight].
 */
@Component interface SsoSkylightComponent {

    val skylight: SsoSkylight

    @Component.Factory interface Factory {
        fun create(@BindsInstance okHttpClient: OkHttpClient): SsoSkylightComponent
    }

    companion object {
        fun create() = create(OkHttpClient())
        fun create(okHttpClient: OkHttpClient) = DaggerSsoSkylightComponent.factory().create(okHttpClient)
    }
}
