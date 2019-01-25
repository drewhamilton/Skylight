package drewhamilton.skylight.sso.inject

import dagger.Component
import drewhamilton.skylight.SkylightRepository

@Component(modules = [SsoSkylightModule::class])
interface SsoSkylightComponent {

    fun skylightRepository(): SkylightRepository

    @Component.Builder
    interface Builder {
//        @BindsInstance fun httpLoggingInterceptor(httpLoggingInterceptor: HttpLoggingInterceptor? = null): Builder
        fun build(): SsoSkylightComponent
    }

    companion object {
        fun builder(): Builder = DaggerSsoSkylightComponent.builder()
    }
}
