package dev.drewhamilton.skylight.sunrise_sunset_org.dagger

import dagger.BindsInstance
import dagger.Component
import dev.drewhamilton.skylight.sunrise_sunset_org.SunriseSunsetOrgSkylight
import okhttp3.OkHttpClient

/**
 * A Dagger component providing an instance of [SunriseSunsetOrgSkylight].
 */
@Component interface SunriseSunsetOrgSkylightComponent {

    val skylight: SunriseSunsetOrgSkylight

    @Component.Factory interface Factory {
        fun create(@BindsInstance okHttpClient: OkHttpClient): SunriseSunsetOrgSkylightComponent
    }

    companion object {
        fun create() = create(OkHttpClient())
        fun create(okHttpClient: OkHttpClient) = DaggerSunriseSunsetOrgSkylightComponent.factory().create(okHttpClient)
    }
}
