package drewhamilton.skylight.views.test.view

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import drewhamilton.skylight.views.test.CustomActions
import drewhamilton.skylight.views.test.UiTest
import org.junit.Assert.fail

abstract class ViewTest<V : View> : UiTest<ViewTestActivity>() {

    final override val activityClass
        get() = ViewTestActivity::class.java

    @Suppress("UNCHECKED_CAST")
    protected fun getView() = activity.getContentView().getChildAt(0) as V

    protected fun setView(view: V) {
        runOnUiThread {
            activity.getContentView().removeAllViews()
            activity.setContentView(view)
        }
        CustomActions.waitForUiThread()
    }

    protected fun setView(@LayoutRes layout: Int) {
        runOnUiThread {
            activity.getContentView().removeAllViews()
            activity.setContentView(layout)
            try {
                getView()
            } catch (castException: ClassCastException) {
                fail("View inflated from layout res <$layout> was not an instance of V")
            }
        }
        CustomActions.waitForUiThread()
    }

    private fun ViewTestActivity.getContentView() = findViewById<ViewGroup>(android.R.id.content)
}
