package drewhamilton.skylight.sso.inject

import dagger.Module
import dagger.Provides
import dagger.Reusable
import drewhamilton.skylight.RxSkylightRepository

/**
 * Use this module if you have no other implementations of [RxSkylightRepository] in your app and you don't want to use
 * the [Sso] annotation each time you inject classes from this module.
 */
@Module(includes = [SsoSkylightModule::class])
class UnqualifiedSsoSkylightModule {

    @Provides
    @Reusable
    fun skylightRepository(@Sso skylightRepository: RxSkylightRepository) = skylightRepository
}
