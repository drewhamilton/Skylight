package drewhamilton.skylight.sample

import dagger.Module
import dagger.Provides
import dagger.Reusable
import drewhamilton.skylight.Skylight
import drewhamilton.skylight.sso.inject.SsoSkylightComponent

@Module
object SkylightModule {

    @JvmStatic
    @Provides
    @Reusable
    fun skylight(): Skylight = SsoSkylightComponent.default().skylight()
}
