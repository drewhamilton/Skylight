package drewhamilton.skylight.sample

import android.app.Application

class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val appComponent = DaggerAppComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
        ActivityInjector.instance = appComponent
    }
}
