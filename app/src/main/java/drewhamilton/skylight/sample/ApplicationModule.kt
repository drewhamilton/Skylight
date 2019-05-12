package drewhamilton.skylight.sample

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.Reusable
import drewhamilton.rxpreferences.dagger.RxPreferencesComponent
import javax.inject.Singleton

@Module
object ApplicationModule {

    @JvmStatic
    @Provides
    @Singleton
    fun applicationContext(application: Application): Context = application.applicationContext

    @JvmStatic
    @Provides
    @Reusable
    fun sharedPreferences(application: Application): SharedPreferences =
        application.getSharedPreferences("drewhamilton.skylight.sample", Context.MODE_PRIVATE)

    @JvmStatic
    @Provides
    @Reusable
    fun rxPreferences(sharedPreferences: SharedPreferences) =
        RxPreferencesComponent.create(sharedPreferences).rxPreferences()
}
