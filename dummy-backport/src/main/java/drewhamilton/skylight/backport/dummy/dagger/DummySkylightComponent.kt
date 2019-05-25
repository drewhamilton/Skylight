package drewhamilton.skylight.backport.dummy.dagger

import dagger.BindsInstance
import dagger.Component
import drewhamilton.skylight.backport.SkylightDay
import drewhamilton.skylight.backport.dummy.DummySkylight

/**
 * A Dagger component providing an instance of [DummySkylight].
 */
@Component
interface DummySkylightComponent {

    fun skylight(): DummySkylight

    @Component.Factory interface Factory {
        fun create(@BindsInstance dummySkylightDay: SkylightDay): DummySkylightComponent
    }

    companion object {
        fun create(dummySkylightDay: SkylightDay): DummySkylightComponent =
            DaggerDummySkylightBackportComponent.factory().create(dummySkylightDay)
    }
}
