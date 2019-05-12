package drewhamilton.skylight.sample.source

import dagger.Reusable
import drewhamilton.rxpreferences.RxPreferences
import drewhamilton.rxpreferences.edit
import drewhamilton.rxpreferences.putEnum
import javax.inject.Inject

@Reusable
class MutableSkylightRepository @Inject constructor(preferences: RxPreferences) : SkylightRepository(preferences) {

    fun selectSkylightType(skylightType: SkylightType) = preferences.edit {
        putEnum(Keys.SKYLIGHT_TYPE, skylightType)
    }
}
