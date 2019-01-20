package drewhamilton.skylight.sample.main

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import drewhamilton.skylight.SkylightRepository
import drewhamilton.skylight.models.AlwaysLight
import drewhamilton.skylight.models.NeverDaytime
import drewhamilton.skylight.models.SkylightInfo
import drewhamilton.skylight.models.Typical
import drewhamilton.skylight.sample.AppComponent
import drewhamilton.skylight.sample.R
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

        val locationOptions = locationRepository.getLocationOptions()
        locationSelector.adapter = LocationSpinnerAdapter(this, locationOptions)

        locationSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val location = locationOptions[position]
                skylightRepository.getSkylightInfo(location.coordinates, Date())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(Consumer { it.display() })
                    .disposeOnDestroyView()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        locationSelector.setSelection(0)
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
