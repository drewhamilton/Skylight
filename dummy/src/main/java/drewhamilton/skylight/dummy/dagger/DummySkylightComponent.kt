package drewhamilton.skylight.dummy.dagger

import dagger.BindsInstance
import dagger.Component
import drewhamilton.skylight.NewSkylightDay
import drewhamilton.skylight.dummy.DummySkylight

/**
 * A Dagger component providing an instance of [DummySkylight].
 */
@Component
interface DummySkylightComponent {

    fun skylight(): DummySkylight

    @Component.Factory interface Factory {
        fun create(@BindsInstance dummySkylightDay: NewSkylightDay): DummySkylightComponent
    }

    companion object {
        fun create(dummySkylightDay: NewSkylightDay): DummySkylightComponent =
            DaggerDummySkylightComponent.factory().create(dummySkylightDay)
    }
}
