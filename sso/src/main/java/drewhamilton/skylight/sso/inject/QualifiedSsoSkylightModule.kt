package drewhamilton.skylight.sso.inject

import dagger.Module
import dagger.Provides
import dagger.Reusable
import drewhamilton.skylight.SkylightRepository
import drewhamilton.skylight.sso.SsoSkylightRepository
import drewhamilton.skylight.sso.network.InfoClient

/**
 * Use this module if you have multiple implementations of [SkylightRepository] in the same scope.
 */
@Module(includes = [SsoNetworkModule::class])
class QualifiedSsoSkylightModule {

    @Provides
    @Reusable
    @Sso
    fun skylightRepository(client: InfoClient): SkylightRepository = SsoSkylightRepository(client)
}
