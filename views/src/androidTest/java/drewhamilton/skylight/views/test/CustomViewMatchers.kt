package drewhamilton.skylight.views.test

import android.view.View
import android.widget.TextView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`

object CustomViewMatchers {

    fun withTextSize(textSizePx: Float) = object : BoundedMatcher<View, TextView>(TextView::class.java) {

        private val floatMatcher: Matcher<Float> = `is`(textSizePx)

        override fun describeTo(description: Description?) {
            description?.appendText("with text size: ")
            floatMatcher.describeTo(description)
        }

        override fun matchesSafely(item: TextView?): Boolean {
            if (item == null) return false

            return floatMatcher.matches(item.textSize)
        }
    }
}
