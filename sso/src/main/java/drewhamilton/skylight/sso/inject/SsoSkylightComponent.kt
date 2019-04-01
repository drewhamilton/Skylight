package drewhamilton.skylight.sso.inject

import dagger.BindsInstance
import dagger.Component
import drewhamilton.skylight.SkylightRepository
import okhttp3.OkHttpClient

@Component(modules = [SsoSkylightModule::class])
interface SsoSkylightComponent {

    fun skylightRepository(): SkylightRepository

    @Component.Builder
    interface Builder {
        @BindsInstance fun okHttpClient(okHttpClient: OkHttpClient = OkHttpClient()): Builder
        fun build(): SsoSkylightComponent
    }

    companion object {
        fun builder(): Builder = DaggerSsoSkylightComponent.builder()
            .okHttpClient()

        fun default() = builder().build()
    }
}
