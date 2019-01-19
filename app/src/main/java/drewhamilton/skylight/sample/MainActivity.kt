package drewhamilton.skylight.sample

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import drewhamilton.skylight.sample.location.LocationRepository

class MainActivity : AppCompatActivity() {

    private lateinit var locationRepository: LocationRepository

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_main)

        // TODO: Use Dagger
        locationRepository = LocationRepository(this)
    }
}
