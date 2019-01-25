package drewhamilton.skylight.sso.inject

import dagger.BindsInstance
import dagger.Component
import drewhamilton.skylight.SkylightRepository
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import okhttp3.logging.HttpLoggingInterceptor

@Component(modules = [SsoSkylightModule::class])
interface SsoSkylightComponent {

    fun skylightRepository(): SkylightRepository

    @Component.Builder
    interface Builder {
        @BindsInstance fun httpLoggingInterceptor(httpLoggingInterceptor: HttpLoggingInterceptor? = null): Builder
        @BindsInstance fun networkScheduler(networkScheduler: Scheduler = Schedulers.io())
        fun build(): SsoSkylightComponent
    }

    companion object {
        fun builder(): Builder = DaggerSsoSkylightComponent.builder()
    }
}
