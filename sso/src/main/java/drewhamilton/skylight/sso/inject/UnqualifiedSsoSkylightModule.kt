package drewhamilton.skylight.sso.inject

import dagger.Module
import dagger.Provides
import drewhamilton.skylight.AbstractSkylightRepository

/**
 * Use this module if you have no other implementations of [AbstractSkylightRepository] in your app and you don't want to use
 * the [Sso] annotation each time you inject classes from this module.
 */
@Module(includes = [SsoSkylightModule::class])
class UnqualifiedSsoSkylightModule {

  @Provides
  fun skylightRepository(@Sso skylightRepository: AbstractSkylightRepository) = skylightRepository
}
