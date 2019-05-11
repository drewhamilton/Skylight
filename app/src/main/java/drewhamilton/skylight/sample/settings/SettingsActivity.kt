package drewhamilton.skylight.sample.settings

import android.os.Bundle
import drewhamilton.skylight.sample.R
import drewhamilton.skylight.sample.rx.ui.RxActivity
import kotlinx.android.synthetic.main.settings_destination.networkButton
import kotlinx.android.synthetic.main.settings_destination.toolbar

class SettingsActivity : RxActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_destination)

        toolbar.setNavigationOnClickListener { finish() }
        networkButton.isChecked = true
    }
}
