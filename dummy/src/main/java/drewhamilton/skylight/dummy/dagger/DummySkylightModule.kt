package drewhamilton.skylight.dummy.dagger

import dagger.Module
import dagger.Provides
import drewhamilton.skylight.SkylightDay
import drewhamilton.skylight.dummy.DummySkylight

@Module
internal object DummySkylightModule {

    @JvmStatic
    @Provides
    fun skylight(dummySkylightDay: SkylightDay) = DummySkylight(dummySkylightDay)
}
