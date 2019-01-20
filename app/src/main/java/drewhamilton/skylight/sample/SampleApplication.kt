package drewhamilton.skylight.sample

import android.app.Application

@Suppress("Unused")
class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AppComponent.instance = DaggerAppComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }
}
