package drewhamilton.skylight.sso.datetime

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.text.DateFormat
import java.util.Date
import java.util.TimeZone

class JavaDateFormatWrapperTest {

    private val dummyDate = Date(-9876L)
    private val dummyString = "Dummy string"

    private lateinit var mockJavaDateFormat: DateFormat

    private lateinit var javaDateFormatWrapper: JavaDateFormatWrapper

    @Before
    fun setUp() {
        mockJavaDateFormat = mock {
            on { format(dummyDate) } doReturn dummyString
            on { parse(dummyString) } doReturn dummyDate
        }
    }

    @Test
    fun `init with explicit time zone sets that time zone`() {
        val testTimeZone = TimeZone.getTimeZone("EST")
        javaDateFormatWrapper = JavaDateFormatWrapper(mockJavaDateFormat, testTimeZone)
        verify(mockJavaDateFormat).timeZone = testTimeZone
        verifyNoMoreInteractions(mockJavaDateFormat)
    }

    @Test
    fun `init without time zone sets no time zone`() {
        javaDateFormatWrapper = JavaDateFormatWrapper(mockJavaDateFormat)
        verify(mockJavaDateFormat, never()).timeZone = any()
        verifyNoMoreInteractions(mockJavaDateFormat)
    }

    @Test
    fun `format forwards to javaDateFormat`() {
        javaDateFormatWrapper = JavaDateFormatWrapper(mockJavaDateFormat)

        assertEquals(dummyString, javaDateFormatWrapper.format(dummyDate))
        verify(mockJavaDateFormat).format(dummyDate)
        verifyNoMoreInteractions(mockJavaDateFormat)
    }

    @Test
    fun `parse forwards to javaDateFormat`() {
        javaDateFormatWrapper = JavaDateFormatWrapper(mockJavaDateFormat)

        assertEquals(dummyDate, javaDateFormatWrapper.parse(dummyString))
        verify(mockJavaDateFormat).parse(dummyString)
        verifyNoMoreInteractions(mockJavaDateFormat)
    }
}
