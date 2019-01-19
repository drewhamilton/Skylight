package drewhamilton.skylight.sso

import drewhamilton.skylight.RxSkylightRepository
import drewhamilton.skylight.SkylightRepository
import drewhamilton.skylight.models.Coordinates
import drewhamilton.skylight.models.SkylightInfo
import drewhamilton.skylight.sso.network.InfoClient
import java.util.*
import javax.inject.Inject

class SsoSkylightRepository @Inject constructor(client: InfoClient) : SkylightRepository {

    private val internalRxRepository: RxSkylightRepository = SsoRxSkylightRepository(client)

    override fun getSkylightInfo(coordinates: Coordinates, date: Date): SkylightInfo =
        internalRxRepository.getSkylightInfo(coordinates, date).blockingGet()
}
