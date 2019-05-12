package drewhamilton.skylight.sample.settings

import android.os.Bundle
import drewhamilton.skylight.sample.AppComponent
import drewhamilton.skylight.sample.R
import drewhamilton.skylight.sample.rx.ui.RxActivity
import drewhamilton.skylight.sample.source.MutableSkylightRepository
import drewhamilton.skylight.sample.source.SkylightRepository
import kotlinx.android.synthetic.main.settings_destination.dummyButton
import kotlinx.android.synthetic.main.settings_destination.localButton
import kotlinx.android.synthetic.main.settings_destination.networkButton
import kotlinx.android.synthetic.main.settings_destination.sourceSelection
import kotlinx.android.synthetic.main.settings_destination.toolbar
import javax.inject.Inject

class SettingsActivity : RxActivity() {

    @Suppress("ProtectedInFinal")
    @Inject protected lateinit var skylightRepository: MutableSkylightRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        AppComponent.instance.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_destination)

        toolbar.setNavigationOnClickListener { finish() }

        skylightRepository.observeSelectedSkylightType()
            .subscribe {
                when (it!!) {
                    SkylightRepository.SkylightType.SSO -> networkButton.isChecked = true
                    SkylightRepository.SkylightType.CALCULATOR -> localButton.isChecked = true
                    SkylightRepository.SkylightType.DUMMY -> dummyButton.isChecked = true
                }
            }
            .disposeOnDestroyView()

        sourceSelection.setOnCheckedChangeListener { _, checkedId ->
            val selectedType = when (checkedId) {
                R.id.localButton -> SkylightRepository.SkylightType.CALCULATOR
                R.id.dummyButton -> SkylightRepository.SkylightType.DUMMY
                else -> SkylightRepository.SkylightType.SSO
            }
            skylightRepository.selectSkylightType(selectedType).subscribe()
        }
    }
}
