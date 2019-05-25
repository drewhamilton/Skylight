package drewhamilton.skylight.backport.dummy.dagger

import dagger.BindsInstance
import dagger.Component
import drewhamilton.skylight.backport.SkylightDayBackport
import drewhamilton.skylight.backport.dummy.DummySkylightBackport

/**
 * A Dagger component providing an instance of [DummySkylightBackport].
 */
@Component
interface DummySkylightBackportComponent {

    fun skylight(): DummySkylightBackport

    @Component.Factory interface Factory {
        fun create(@BindsInstance dummySkylightDay: SkylightDayBackport): DummySkylightBackportComponent
    }

    companion object {
        fun create(dummySkylightDay: SkylightDayBackport): DummySkylightBackportComponent =
            DaggerDummySkylightBackportComponent.factory().create(dummySkylightDay)
    }
}
