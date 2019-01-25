package drewhamilton.skylight.sso.inject

import dagger.BindsInstance
import dagger.Component
import drewhamilton.skylight.SkylightRepository
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient

@Component(modules = [SsoSkylightModule::class])
interface SsoSkylightComponent {

    fun skylightRepository(): SkylightRepository

    @Component.Builder
    interface Builder {
        @BindsInstance fun okHttpClient(okHttpClient: OkHttpClient = OkHttpClient()): Builder
        @BindsInstance fun networkScheduler(networkScheduler: Scheduler = Schedulers.io()): Builder
        fun build(): SsoSkylightComponent
    }

    companion object {
        fun builder(): Builder = DaggerSsoSkylightComponent.builder()
            .okHttpClient()
            .networkScheduler()

        fun default() = builder().build()
    }
}
