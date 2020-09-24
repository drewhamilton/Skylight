package dev.drewhamilton.skylight.fake.dagger

import dagger.BindsInstance
import dagger.Component
import dev.drewhamilton.skylight.SkylightDay
import dev.drewhamilton.skylight.fake.FakeSkylight

/**
 * A Dagger component providing an instance of [FakeSkylight].
 */
@Component interface FakeSkylightComponent {

    val skylight: FakeSkylight

    @Component.Factory interface Factory {
        fun create(@BindsInstance dummySkylightDay: SkylightDay): FakeSkylightComponent
    }

    companion object {
        fun create(dummySkylightDay: SkylightDay): FakeSkylightComponent =
            DaggerFakeSkylightComponent.factory().create(dummySkylightDay)
    }
}
