package drewhamilton.skylight.sample

import android.os.Bundle
import drewhamilton.skylight.SkylightRepository
import drewhamilton.skylight.models.AlwaysLight
import drewhamilton.skylight.models.NeverDaytime
import drewhamilton.skylight.models.SkylightInfo
import drewhamilton.skylight.models.Typical
import drewhamilton.skylight.sample.location.LocationRepository
import drewhamilton.skylight.sample.rx.ui.RxActivity
import drewhamilton.skylight.views.event.setTime
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject

class MainActivity : RxActivity() {

    @Inject protected lateinit var locationRepository: LocationRepository
    @Inject protected lateinit var skylightRepository: SkylightRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        initializeDependencies()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val location = locationRepository.getSelectedLocation()
        skylightRepository.getSkylightInfo(location.coordinates, Date())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer { it.display() })
            .disposeOnDestroyView()
    }

    private fun initializeDependencies() {
        AppComponent.instance.inject(this)
    }

    private fun SkylightInfo.display() {
        var dawnDateTime: Date? = null
        var sunriseDateTime: Date? = null
        var sunsetDateTime: Date? = null
        var duskDateTime: Date? = null

        when (this) {
            is Typical -> {
                dawnDateTime = dawn
                sunriseDateTime = sunrise
                sunsetDateTime = sunset
                duskDateTime = dusk
            }
            is AlwaysLight -> {
                sunriseDateTime = sunrise
                sunsetDateTime = sunset
            }
            is NeverDaytime -> {
                dawnDateTime = dawn
                duskDateTime = dusk
            }
        }

        dawn.setTime(dawnDateTime)
        sunrise.setTime(sunriseDateTime)
        sunset.setTime(sunsetDateTime)
        dusk.setTime(duskDateTime)
    }
}
