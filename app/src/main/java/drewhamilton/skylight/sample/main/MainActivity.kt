package drewhamilton.skylight.sample.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import drewhamilton.skylight.Skylight
import drewhamilton.skylight.SkylightDay
import drewhamilton.skylight.rx.getSkylightDaySingle
import drewhamilton.skylight.sample.AppComponent
import drewhamilton.skylight.sample.BuildConfig
import drewhamilton.skylight.sample.R
import drewhamilton.skylight.sample.location.Location
import drewhamilton.skylight.sample.location.LocationRepository
import drewhamilton.skylight.sample.rx.ui.RxActivity
import drewhamilton.skylight.sample.settings.SettingsActivity
import drewhamilton.skylight.views.event.SkylightEventView
import drewhamilton.skylight.views.event.setTime
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.main_destination.dawn
import kotlinx.android.synthetic.main.main_destination.dusk
import kotlinx.android.synthetic.main.main_destination.locationSelector
import kotlinx.android.synthetic.main.main_destination.sunrise
import kotlinx.android.synthetic.main.main_destination.sunset
import kotlinx.android.synthetic.main.main_destination.toolbar
import kotlinx.android.synthetic.main.main_destination.version
import java.text.DateFormat
import java.util.Date
import java.util.TimeZone
import javax.inject.Inject

class MainActivity : RxActivity() {

    @Suppress("ProtectedInFinal")
    @Inject protected lateinit var locationRepository: LocationRepository

    @Suppress("ProtectedInFinal")
    @Inject protected lateinit var skylight: Skylight

    private val timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_destination)
        version.text = getString(R.string.version_info, BuildConfig.VERSION_NAME)
        initializeMenu()

        AppComponent.instance.inject(this)
        initializeLocationOptions()
    }

    override fun onResume() {
        super.onResume()
        // Inject again in onResume because settings activity may change what is injected:
        AppComponent.instance.inject(this)

        // Re-display selected item in case something has changed:
        (locationSelector.selectedItem as Location).display()
    }

    private fun initializeLocationOptions() {
        val locationOptions = locationRepository.getLocationOptions()
        locationSelector.adapter = LocationSpinnerAdapter(this, locationOptions)

        locationSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) =
                locationOptions[position].display()

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        locationSelector.setSelection(0)
    }

    private fun Location.display() {
        skylight.getSkylightDaySingle(coordinates, Date())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { timeFormat.timeZone = timeZone }
            .subscribe(Consumer { it.display() })
            .disposeOnDestroyView()
    }

    private fun SkylightDay.display() {
        var dawnDateTime: Date? = null
        var sunriseDateTime: Date? = null
        var sunsetDateTime: Date? = null
        var duskDateTime: Date? = null

        when (this) {
            is SkylightDay.Typical -> {
                dawnDateTime = dawn
                sunriseDateTime = sunrise
                sunsetDateTime = sunset
                duskDateTime = dusk
            }
            is SkylightDay.AlwaysLight -> {
                sunriseDateTime = sunrise
                sunsetDateTime = sunset
            }
            is SkylightDay.NeverDaytime -> {
                dawnDateTime = dawn
                duskDateTime = dusk
            }
        }

        dawn.setTime(dawnDateTime, timeFormat, R.string.never)
        sunrise.setTime(sunriseDateTime, timeFormat, R.string.never)
        sunset.setTime(sunsetDateTime, timeFormat, R.string.never)
        dusk.setTime(duskDateTime, timeFormat, R.string.never)

        dawn.showDetailsOnClick(timeFormat.timeZone)
        sunrise.showDetailsOnClick(timeFormat.timeZone)
        sunset.showDetailsOnClick(timeFormat.timeZone)
        dusk.showDetailsOnClick(timeFormat.timeZone)
    }

    private fun SkylightEventView.showDetailsOnClick(timeZone: TimeZone) {
        val clickListener: View.OnClickListener? = if (timeText.isNotEmpty())
            View.OnClickListener {
                MaterialAlertDialogBuilder(this@MainActivity)
                    .setTitle(labelText)
                    .setMessage("$timeText, ${timeZone.displayName}")
                    .setPositiveButton(R.string.good_to_know) { _, _ -> }
                    .show()
            }
        else
            null
        setOnClickListener(clickListener)
    }

    private fun initializeMenu() {
        val settingsItem = toolbar.menu.findItem(R.id.settings)
        settingsItem.setOnMenuItemClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
            true
        }
    }
}
