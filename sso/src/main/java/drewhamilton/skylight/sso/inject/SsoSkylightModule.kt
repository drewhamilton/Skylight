package drewhamilton.skylight.sso.inject

import dagger.Module
import dagger.Provides
import dagger.Reusable
import drewhamilton.skylight.SkylightRepository

/**
 * Use this module if you have no other implementations of [SkylightRepository] in your scope. If you are using multiple
 * instances of [SkylightRepository] in the same scope, you can use [QualifiedSsoSkylightModule] along with the [Sso]
 * qualifier instead.
 */
@Module(includes = [QualifiedSsoSkylightModule::class])
class SsoSkylightModule {

    @Provides
    @Reusable
    fun skylightRepository(@Sso skylightRepository: SkylightRepository) = skylightRepository
}
