package drewhamilton.skylight.sso.inject

import dagger.Module
import dagger.Provides
import drewhamilton.skylight.sso.SsoSkylight
import drewhamilton.skylight.sso.network.InfoClient

@Module(includes = [SsoNetworkModule::class])
internal object SsoSkylightModule {

    @JvmStatic
    @Provides
    fun skylight(client: InfoClient) = SsoSkylight(client)
}
