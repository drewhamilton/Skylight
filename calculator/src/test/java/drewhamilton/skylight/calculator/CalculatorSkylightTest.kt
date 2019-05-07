package drewhamilton.skylight.calculator

import drewhamilton.skylight.SkylightDay
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Ignore
import org.junit.Test
import java.util.Date

class CalculatorSkylightTest {

  private val skylight = CalculatorSkylight()

  //region Amsterdam on January 6, 2019
  @Ignore("Returned values are not exactly the same given identical input location and same day/different time input")
  @Test
  fun `Amsterdam on January 6, 2019 early morning is typical`() =
    testAmsterdam_2019january6(TestDateTimes.JANUARY_6_2019_EARLY.asDate())

  @Ignore("Returned values are not exactly the same given identical input location and same day/different time input")
  @Test
  fun `Amsterdam on January 6, 2019 morning is typical`() =
    testAmsterdam_2019january6(TestDateTimes.JANUARY_6_2019_MORNING.asDate())

  @Test
  fun `Amsterdam on January 6, 2019 noon is typical`() =
    testAmsterdam_2019january6(TestDateTimes.JANUARY_6_2019_NOON.asDate())

  @Ignore("Returned values are not exactly the same given identical input location and same day/different time input")
  @Test
  fun `Amsterdam on January 6, 2019 afternoon is typical`() =
    testAmsterdam_2019january6(TestDateTimes.JANUARY_6_2019_AFTERNOON.asDate())

  @Ignore("Returned values are not exactly the same given identical input location and same day/different time input")
  @Test
  fun `Amsterdam on January 6, 2019 late night is typical`() =
    testAmsterdam_2019january6(TestDateTimes.JANUARY_6_2019_LATE.asDate())

  private fun testAmsterdam_2019january6(inputDateTime: Date) {
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
  //endregion

  //region Svalbard on January 6, 2019
  @Test
  fun `Svalbard on January 6, 2019 early morning is never light`() =
    testSvalbard_2019january6(TestDateTimes.JANUARY_6_2019_EARLY.asDate())

  @Test
  fun `Svalbard on January 6, 2019 morning is never light`() =
    testSvalbard_2019january6(TestDateTimes.JANUARY_6_2019_MORNING.asDate())

  @Test
  fun `Svalbard on January 6, 2019 noon is never light`() =
    testSvalbard_2019january6(TestDateTimes.JANUARY_6_2019_NOON.asDate())

  @Test
  fun `Svalbard on January 6, 2019 afternoon is never light`() =
    testSvalbard_2019january6(TestDateTimes.JANUARY_6_2019_AFTERNOON.asDate())

  @Test
  fun `Svalbard on January 6, 2019 late night is never light`() =
    testSvalbard_2019january6(TestDateTimes.JANUARY_6_2019_LATE.asDate())

  private fun testSvalbard_2019january6(inputDateTime: Date) {
    val result = skylight.getSkylightDay(TestCoordinates.SVALBARD, inputDateTime)
    assertEquals(SkylightDay.NeverLight, result)
  }
  //endregion
}
