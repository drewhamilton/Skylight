package drewhamilton.skylight.sso

import drewhamilton.skylight.SkylightRepository
import drewhamilton.skylight.models.Coordinates
import drewhamilton.skylight.models.SkylightInfo
import drewhamilton.skylight.sso.conversions.toSkylightInfo
import drewhamilton.skylight.sso.network.InfoClient
import drewhamilton.skylight.sso.network.models.Params
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class SsoSkylightRepository @Inject constructor(private val client: InfoClient) : SkylightRepository {

    override fun getSkylightInfo(coordinates: Coordinates, date: Date): Single<SkylightInfo> {
        val params = Params(coordinates.latitude, coordinates.longitude, date)
        return client.getInfo(params)
            .map { it.toSkylightInfo() }
    }
}
