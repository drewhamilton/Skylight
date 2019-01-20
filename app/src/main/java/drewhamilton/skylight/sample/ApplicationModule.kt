package drewhamilton.skylight.sample

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    fun application() = application

    @Provides
    @Singleton
    fun applicationContext(): Context = application.applicationContext
}
