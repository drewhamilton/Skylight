package drewhamilton.skylight.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import drewhamilton.skylight.SkylightRepository
import drewhamilton.skylight.sample.location.LocationRepository
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject protected lateinit var locationRepository: LocationRepository
    @Inject protected lateinit var skylightRepository: SkylightRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        ActivityInjector.instance.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val location = locationRepository.getSelectedLocation()
        val skylightInfo = skylightRepository.getSkylightInfo(location.coordinates, Date())
    }
}
