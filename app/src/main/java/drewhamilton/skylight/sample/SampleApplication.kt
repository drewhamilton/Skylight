package drewhamilton.skylight.sample

import android.app.Application
import drewhamilton.skylight.SkylightDay
import drewhamilton.skylight.dummy.dagger.DummySkylightComponent
import drewhamilton.skylight.sso.dagger.SsoSkylightComponent
import java.time.LocalDate

@Suppress("Unused")
class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AppComponent.create(
            this,
            SsoSkylightComponent.create(),
            DummySkylightComponent.create(SkylightDay.NeverLight(LocalDate.now()))
        )
    }
}
