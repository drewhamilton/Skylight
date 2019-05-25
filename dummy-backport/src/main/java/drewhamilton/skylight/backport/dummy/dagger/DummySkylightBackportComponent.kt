package drewhamilton.skylight.backport.dummy.dagger

import dagger.BindsInstance
import dagger.Component
import drewhamilton.skylight.backport.SkylightDay
import drewhamilton.skylight.backport.dummy.DummySkylightBackport

/**
 * A Dagger component providing an instance of [DummySkylightBackport].
 */
@Component
interface DummySkylightBackportComponent {

    fun skylight(): DummySkylightBackport

    @Component.Factory interface Factory {
        fun create(@BindsInstance dummySkylightDay: SkylightDay): DummySkylightBackportComponent
    }

    companion object {
        fun create(dummySkylightDay: SkylightDay): DummySkylightBackportComponent =
            DaggerDummySkylightBackportComponent.factory().create(dummySkylightDay)
    }
}
