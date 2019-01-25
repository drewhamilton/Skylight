package drewhamilton.skylight.sso.inject

import dagger.Module
import dagger.Provides
import drewhamilton.skylight.SkylightRepository
import drewhamilton.skylight.sso.SsoSkylightRepository
import drewhamilton.skylight.sso.network.InfoClient

@Module(includes = [SsoNetworkModule::class])
internal object SsoSkylightModule {

    @JvmStatic
    @Provides
    fun skylightRepository(client: InfoClient): SkylightRepository = SsoSkylightRepository(client)
}
