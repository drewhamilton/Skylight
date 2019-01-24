package drewhamilton.skylight.sso.dates

import com.nhaarman.mockitokotlin2.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.text.DateFormat
import java.util.*

class JavaDateFormatWrapperTest {

    private val defaultTimeZone = TimeZone.getTimeZone("UTC")

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
    fun `init without time zone sets default time zone`() {
        javaDateFormatWrapper = JavaDateFormatWrapper(mockJavaDateFormat)
        verify(mockJavaDateFormat).timeZone = defaultTimeZone
        verifyNoMoreInteractions(mockJavaDateFormat)
    }

    @Test
    fun `format forwards to javaDateFormat`() {
        javaDateFormatWrapper = JavaDateFormatWrapper(mockJavaDateFormat)
        verify(mockJavaDateFormat).timeZone = any()

        assertEquals(dummyString, javaDateFormatWrapper.format(dummyDate))
        verify(mockJavaDateFormat).format(dummyDate)
        verifyNoMoreInteractions(mockJavaDateFormat)
    }

    @Test
    fun `parse forwards to javaDateFormat`() {
        javaDateFormatWrapper = JavaDateFormatWrapper(mockJavaDateFormat)
        verify(mockJavaDateFormat).timeZone = any()

        assertEquals(dummyDate, javaDateFormatWrapper.parse(dummyString))
        verify(mockJavaDateFormat).parse(dummyString)
        verifyNoMoreInteractions(mockJavaDateFormat)
    }
}
