package drewhamilton.skylight.backport.sso.dagger

import dagger.BindsInstance
import dagger.Component
import drewhamilton.skylight.backport.sso.SsoSkylightBackport
import drewhamilton.skylight.sso.dagger.SsoNetworkModule
import okhttp3.OkHttpClient

/**
 * A Dagger component providing an instance of [SsoSkylightBackport].
 */
@Component(modules = [SsoNetworkModule::class])
interface SsoSkylightBackportComponent {

    fun skylight(): SsoSkylightBackport

    @Component.Factory interface Factory {
        fun create(@BindsInstance okHttpClient: OkHttpClient): SsoSkylightBackportComponent
    }

    companion object {

        /*
         * TODO WORKAROUND: The second function forces consumers to include the OkHttp dependency, even when relying
         *  on the default parameters, even when @JvmOverloads is applied. This method allows consumers to not import
         *  OkHttp if they don't want to.
         */
        fun create() = create(OkHttpClient())

        fun create(okHttpClient: OkHttpClient = OkHttpClient()) =
            DaggerSsoSkylightBackportComponent.factory().create(okHttpClient)
    }
}
