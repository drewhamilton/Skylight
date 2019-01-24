package drewhamilton.skylight.views.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.intent.rule.IntentsTestRule
import org.junit.Assert.fail
import org.junit.Rule

abstract class UiTest<A : AppCompatActivity> {

    @Suppress("LeakingThis")
    @get:Rule
    val testRule = IntentsTestRule(activityClass, true, false)

    protected abstract val activityClass: Class<A>

    protected val activity: A
        get() = testRule.activity

    protected fun launchActivity(intent: Intent = Intent()) {
        testRule.launchActivity(intent)
        onActivityLaunched()
    }

    protected open fun onActivityLaunched() {}

    protected fun runOnUiThread(runnable: () -> Unit) =
        try {
            testRule.runOnUiThread(runnable)
        } catch (exception: Exception) {
            fail("${exception.javaClass.simpleName} while running on UI thread: ${exception.message}")
        }

    protected fun simulateConfigurationChange() {
        runOnUiThread { activity.recreate() }
        CustomActions.waitForUiThread()
    }
}
