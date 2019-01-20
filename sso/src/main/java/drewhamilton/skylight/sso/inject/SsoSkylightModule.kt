package drewhamilton.skylight.sso.inject

import dagger.Module
import dagger.Provides
import dagger.Reusable
import drewhamilton.skylight.SkylightRepository
import drewhamilton.skylight.sso.SsoSkylightRepository
import drewhamilton.skylight.sso.network.InfoClient

/**
 * Use this module if you have no other implementations of [SkylightRepository] in your scope. If you are using multiple
 * instances of [SkylightRepository] in the same scope, you can use [QualifiedSsoSkylightModule] along with the [Sso]
 * qualifier instead.
 */
@Module(includes = [SsoNetworkModule::class])
class SsoSkylightModule {

    @Provides
    @Reusable
    fun skylightRepository(client: InfoClient): SkylightRepository = SsoSkylightRepository(client)
}
