package drewhamilton.skylight.views.event

import android.content.Context
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Before
import org.junit.Test
import java.text.DateFormat
import java.util.Date

class SkylightEventViewExtensionsTest {

    private val dummyDate = Date(3412498)
    private val dummyDateString = "Dummy date string"

    private val defaultDateFormat = DateFormat.getTimeInstance(DateFormat.SHORT)
    private val defaultDummyDateString = defaultDateFormat.format(dummyDate)

    private val dummyFallbackStringRes = 4321
    private val dummyFallbackString = "Test fallback"

    private lateinit var mockDateFormat: DateFormat

    private lateinit var mockSkylightEventView: SkylightEventView

    private lateinit var mockContext: Context

    @Before
    fun setUp() {
        mockDateFormat = mock {
            on { format(dummyDate) } doReturn dummyDateString
        }

        mockContext = mock {
            on { getString(dummyFallbackStringRes) } doReturn dummyFallbackString
        }
        mockSkylightEventView = mock {
            on { context } doReturn mockContext
        }
    }

    @Test
    fun `setTime(Date?) with non-null date sets formatted date text`() {
        mockSkylightEventView.setTime(dummyDate)
        verifyTimeTextSet(defaultDummyDateString)
    }

    @Test
    fun `setTime(Date?) with null date sets empty text`() {
        mockSkylightEventView.setTime(null as Date?)
        verifyTimeHintSet("")
    }

    @Test
    fun `setTime(Date?, Int) with non-null date sets formatted date text`() {
        mockSkylightEventView.setTime(dummyDate, dummyFallbackStringRes)
        verify(mockSkylightEventView).context
        verifyTimeTextSet(defaultDummyDateString)
    }

    @Test
    fun `setTime(Date?, Int) with null date sets fallback text from resource`() {
        mockSkylightEventView.setTime(null as Date?, dummyFallbackStringRes)
        verify(mockSkylightEventView).context
        verifyTimeHintSet(dummyFallbackString)
    }

    @Test
    fun `setTime(Date?, String) with non-null date sets formatted date text`() {
        mockSkylightEventView.setTime(dummyDate, fallback = dummyFallbackString)
        verifyTimeTextSet(defaultDummyDateString)
    }

    @Test
    fun `setTime(Date?, String) with null date sets fallback text`() {
        mockSkylightEventView.setTime(null as Date?, fallback = dummyFallbackString)
        verifyTimeHintSet(dummyFallbackString)
    }

    @Test
    fun `setTime(Date?, DateFormat) with non-null date sets formatted date text`() {
        mockSkylightEventView.setTime(dummyDate, mockDateFormat)
        verifyTimeTextSet(dummyDateString)
    }

    @Test
    fun `setTime(Date?, DateFormat) with null date sets empty text`() {
        mockSkylightEventView.setTime(null, mockDateFormat)
        verifyTimeHintSet("")
    }

    @Test
    fun `setTime(Date?, DateFormat, Int) with non-null date sets formatted date text`() {
        mockSkylightEventView.setTime(dummyDate, mockDateFormat, dummyFallbackStringRes)
        verify(mockSkylightEventView).context
        verifyTimeTextSet(dummyDateString)
    }

    @Test
    fun `setTime(Date?, DateFormat, Int) with null date sets fallback text from resource`() {
        mockSkylightEventView.setTime(null, mockDateFormat, dummyFallbackStringRes)
        verify(mockSkylightEventView).context
        verifyTimeHintSet(dummyFallbackString)
    }

    @Test
    fun `setTime(Date?, DateFormat, String) with non-null date sets formatted date text`() {
        mockSkylightEventView.setTime(dummyDate, mockDateFormat, dummyFallbackString)
        verifyTimeTextSet(dummyDateString)
    }

    @Test
    fun `setTime(Date?, DateFormat, String) with null date sets fallback text`() {
        mockSkylightEventView.setTime(null, mockDateFormat, dummyFallbackString)
        verifyTimeHintSet(dummyFallbackString)
    }

    private fun verifyTimeTextSet(text: String) {
        verify(mockSkylightEventView).timeText = text
        verify(mockSkylightEventView).timeHint = ""
        verifyNoMoreInteractions(mockSkylightEventView)
    }

    private fun verifyTimeHintSet(hint: String) {
        verify(mockSkylightEventView).timeHint = hint
        verify(mockSkylightEventView).timeText = ""
        verifyNoMoreInteractions(mockSkylightEventView)
    }
}
