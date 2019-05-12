package drewhamilton.skylight.sample.source

import dagger.Reusable
import drewhamilton.rxpreferences.RxPreferences
import drewhamilton.rxpreferences.getEnum
import drewhamilton.rxpreferences.observeEnum
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

@Reusable
open class SkylightRepository @Inject constructor(protected val preferences: RxPreferences) {

    fun observeSelectedSkylightType(): Observable<SkylightType> =
        preferences.observeEnum(Keys.SKYLIGHT_TYPE, Defaults.SKYLIGHT_TYPE)

    fun getSelectedSkylightType(): Single<SkylightType> =
        preferences.getEnum(Keys.SKYLIGHT_TYPE, Defaults.SKYLIGHT_TYPE)

    enum class SkylightType {
        SSO,
        CALCULATOR,
        DUMMY
    }

    protected object Keys {
        const val SKYLIGHT_TYPE = "SkylightType"
    }

    protected object Defaults {
        val SKYLIGHT_TYPE = SkylightType.SSO
    }
}
