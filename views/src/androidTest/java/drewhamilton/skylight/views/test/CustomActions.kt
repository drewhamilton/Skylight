package drewhamilton.skylight.views.test

import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import java.util.concurrent.TimeUnit

object CustomActions {

    fun waitForUiThread() = InstrumentationRegistry.getInstrumentation().waitForIdleSync()

    fun waitForUiThread(time: Int, unit: TimeUnit) =
        Espresso.onView(ViewMatchers.isRoot()).perform(CustomViewActions.loopMainThread(time, unit))

}
