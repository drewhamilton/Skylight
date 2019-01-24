package drewhamilton.skylight.views.test

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import org.hamcrest.Matchers
import java.util.concurrent.TimeUnit

object CustomViewActions {

    fun loopMainThread(time: Int, unit: TimeUnit) = object : ViewAction {
        override fun getConstraints() = Matchers.any<View>(View::class.java)

        override fun getDescription() = "loop main thread for ${toMilliseconds(time, unit)} milliseconds"

        override fun perform(uiController: UiController, view: View) =
            uiController.loopMainThreadForAtLeast(toMilliseconds(time, unit))

        private fun toMilliseconds(time: Int, unit: TimeUnit) = TimeUnit.MILLISECONDS.convert(time.toLong(), unit)
    }
}
