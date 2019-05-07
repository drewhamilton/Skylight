package drewhamilton.skylight.calculator

import drewhamilton.skylight.SkylightDay
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Ignore
import org.junit.Test
import java.util.Date

class CalculatorSkylightTest {

  private val skylight = CalculatorSkylight()

  @Ignore("Returned values are not exactly the same given identical input location and same day/different time input")
  @Test
  fun `Amsterdam on January 6, 2019 early morning`() {
    amsterdam_2019january6(TestDateTimes.JANUARY_6_2019_EARLY.asDate())
  }

  @Ignore("Returned values are not exactly the same given identical input location and same day/different time input")
  @Test
  fun `Amsterdam on January 6, 2019 morning`() {
    amsterdam_2019january6(TestDateTimes.JANUARY_6_2019_MORNING.asDate())
  }

  @Test
  fun `Amsterdam on January 6, 2019 noon`() {
    amsterdam_2019january6(TestDateTimes.JANUARY_6_2019_NOON.asDate())
  }

  @Ignore("Returned values are not exactly the same given identical input location and same day/different time input")
  @Test
  fun `Amsterdam on January 6, 2019 afternoon`() {
    amsterdam_2019january6(TestDateTimes.JANUARY_6_2019_AFTERNOON.asDate())
  }

  @Ignore("Returned values are not exactly the same given identical input location and same day/different time input")
  @Test
  fun `Amsterdam on January 6, 2019 late night`() {
    amsterdam_2019january6(TestDateTimes.JANUARY_6_2019_LATE.asDate())
  }

  private fun amsterdam_2019january6(inputDateTime: Date) {
    val result = skylight.getSkylightDay(
        TestCoordinates.AMSTERDAM,
        inputDateTime
    )
    assertTrue(result is SkylightDay.Typical)
    result as SkylightDay.Typical
    assertEquals(Date(1546758599096), result.dawn)
    assertEquals(Date(1546761171145), result.sunrise)
    assertEquals(Date(1546789260558), result.sunset)
    assertEquals(Date(1546791832608), result.dusk)
  }
}
