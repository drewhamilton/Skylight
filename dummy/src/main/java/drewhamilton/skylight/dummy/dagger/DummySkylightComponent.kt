package drewhamilton.skylight.dummy.dagger

import dagger.BindsInstance
import dagger.Component
import drewhamilton.skylight.SkylightDay
import drewhamilton.skylight.dummy.DummySkylight

/**
 * A Dagger component providing an instance of [DummySkylight].
 */
@Component
interface DummySkylightComponent {

    fun skylight(): DummySkylight

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance dummySkylightDay: SkylightDay): DummySkylightComponent
    }

    companion object {
        fun create(dummySkylightDay: SkylightDay): DummySkylightComponent =
            DaggerDummySkylightComponent.factory().create(dummySkylightDay)
    }
}
