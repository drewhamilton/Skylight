package drewhamilton.skylight.sample

import android.app.Application

class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AppComponent.instance = DaggerAppComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }
}
