package drewhamilton.skylight.views.event

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import drewhamilton.skylight.views.R
import drewhamilton.skylight.views.test.view.ViewTest
import org.hamcrest.Matchers.`is`
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test

class SkylightEventViewTest : ViewTest<SkylightEventView>() {

    private val testLabel
        get() = activity.getString(R.string.testLabel)

    private val testTime
        get() = activity.getString(R.string.testTime)

    private val withNullHint
        get() = withHint(`is`(null as String?))

    @Before
    fun setUp() = launchActivity()

    //region initialize
    @Test
    fun inflate_withoutAttributes_viewHasEmptyTextViews() {
        setView(R.layout.test_skylight_event_view_no_attributes)
        val inflatedView = getView()

        assertEquals("", inflatedView.labelText)
        assertEquals("", inflatedView.timeText)
        assertEquals(null, inflatedView.timeHint)

        onView(withId(R.id.label))
            .check(matches(withText("")))
            .check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))
        onView(withId(R.id.time))
            .check(matches(withText("")))
            .check(matches(withNullHint))
    }

    @Test
    fun inflate_withAttributes_viewHasPopulatedTextViews() {
        setView(R.layout.test_skylight_event_view_with_attributes)
        val inflatedView = getView()

        assertEquals(testLabel, inflatedView.labelText)
        assertEquals(testTime, inflatedView.timeText)
        assertEquals(null, inflatedView.timeHint)

        onView(withId(R.id.label))
            .check(matches(withText(testLabel)))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.time))
            .check(matches(withText(testTime)))
            .check(matches(withNullHint))
    }

    @Test
    fun construct_withContext_viewHasEmptyTextViews() {
        val constructedView = SkylightEventView(activity)
        setView(constructedView)
        assertSame(constructedView, getView())

        assertEquals("", constructedView.labelText)
        assertEquals("", constructedView.timeText)
        assertEquals(null, constructedView.timeHint)

        onView(withId(R.id.label))
            .check(matches(withText("")))
            .check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))
        onView(withId(R.id.time))
            .check(matches(withText("")))
            .check(matches(withNullHint))
    }
    //endregion

    //region setLabelText
    @Test
    fun setLabelText_withStringResource_setsLabelText() {
        setView(SkylightEventView(activity))
        val view = getView()

        runOnUiThread {
            view.setLabelText(R.string.testLabel)
            assertEquals(testLabel, view.labelText)
        }

        onView(withId(R.id.label))
            .check(matches(withText(R.string.testLabel)))
            .check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))
    }

    @Test
    fun setLabelText_withCharSequence_setsLabelText() {
        setView(SkylightEventView(activity))
        val view = getView()

        runOnUiThread {
            view.labelText = testLabel
            assertEquals(testLabel, view.labelText)
        }

        onView(withId(R.id.label))
            .check(matches(withText(testLabel)))
            .check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))
    }
    //endregion
}
