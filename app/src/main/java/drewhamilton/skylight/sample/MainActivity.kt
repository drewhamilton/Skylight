package drewhamilton.skylight.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import drewhamilton.skylight.sample.location.LocationRepository

class MainActivity : AppCompatActivity() {

    private lateinit var locationRepository: LocationRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO: Use Dagger
        locationRepository = LocationRepository(this)
    }
}
