package drewhamilton.skylight.sample

import androidx.multidex.MultiDexApplication
import com.jakewharton.threetenabp.AndroidThreeTen
import drewhamilton.skylight.backport.SkylightDayBackport
import drewhamilton.skylight.backport.dummy.dagger.DummySkylightBackportComponent
import drewhamilton.skylight.backport.sso.dagger.SsoSkylightBackportComponent
import org.threeten.bp.LocalDate

@Suppress("Unused")
class SampleApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this, "TZDB-2019a.dat")

        AppComponent.create(
            this,
            SsoSkylightBackportComponent.create(),
            DummySkylightBackportComponent.create(SkylightDayBackport.NeverLight(LocalDate.now()))
        )
    }
}
