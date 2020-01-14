package drewhamilton.skylight

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import java.time.LocalDate
import java.time.ZonedDateTime

class SkylightDayTest {

    //region SkylightDay.Typical
    @Test(expected = IllegalStateException::class)
    fun `Typical initializer without date throws exception`() {
        SkylightDay.Typical {
            dawn = ZonedDateTime.parse("2020-01-13T07:00:00+02:00")
            sunrise = ZonedDateTime.parse("2020-01-13T09:00:00+02:00")
            sunset = ZonedDateTime.parse("2020-01-13T18:00:00+02:00")
            dusk = ZonedDateTime.parse("2020-01-13T20:00:00+02:00")
        }
    }

    @Test(expected = IllegalStateException::class)
    fun `Typical initializer without any events throws exception`() {
        SkylightDay.Typical {
            date = LocalDate.parse("2020-01-13")
        }
    }

    @Test fun `Typical with same values are equals`() {
        val day1 = SkylightDay.Typical {
            date = LocalDate.parse("2020-01-13")
            dawn = ZonedDateTime.parse("2020-01-13T07:00:00+02:00")
            dusk = ZonedDateTime.parse("2020-01-13T20:00:00+02:00")
        }
        val day2 = SkylightDay.Typical {
            date = LocalDate.parse("2020-01-13")
            dawn = ZonedDateTime.parse("2020-01-13T07:00:00+02:00")
            dusk = ZonedDateTime.parse("2020-01-13T20:00:00+02:00")
        }
        assertEquals(day1, day2)
        assertEquals(day1.hashCode(), day2.hashCode())
    }

    @Test fun `Typical with different values are not equals`() {
        val day1 = SkylightDay.Typical {
            date = LocalDate.parse("2020-01-13")
            sunrise = ZonedDateTime.parse("2020-01-13T07:00:00+02:00")
            sunset = ZonedDateTime.parse("2020-01-13T20:00:00+02:00")
        }
        val day2 = SkylightDay.Typical {
            date = LocalDate.parse("2020-01-13")
            dawn = ZonedDateTime.parse("2020-01-13T07:00:00+02:00")
            dusk = ZonedDateTime.parse("2020-01-13T20:00:00+02:00")
        }
        assertNotEquals(day1, day2)
        assertNotEquals(day1.hashCode(), day2.hashCode())
    }

    @Test fun `Typical DSL assigns expected values`() {
        val date = LocalDate.parse("2020-01-13")
        val dawn = ZonedDateTime.parse("2020-01-13T07:00:00+02:00")
        val sunrise = ZonedDateTime.parse("2020-01-13T09:00:00+02:00")
        val sunset = ZonedDateTime.parse("2020-01-13T18:00:00+02:00")
        val dusk = ZonedDateTime.parse("2020-01-13T20:00:00+02:00")
        val day = SkylightDay.Typical {
            this.date = date
            this.dawn = dawn
            this.sunrise = sunrise
            this.sunset = sunset
            this.dusk = dusk
        }
        assertEquals(date, day.date)
        assertEquals(dawn, day.dawn)
        assertEquals(sunrise, day.sunrise)
        assertEquals(sunset, day.sunset)
        assertEquals(dusk, day.dusk)
    }

    @Test fun `Typical toString returns value string`() {
        val day = SkylightDay.Typical {
            date = LocalDate.parse("2020-01-13")
            dawn = ZonedDateTime.parse("2020-01-13T07:00:00+02:00")
            sunrise = ZonedDateTime.parse("2020-01-13T09:00:00+02:00")
            sunset = ZonedDateTime.parse("2020-01-13T18:00:00+02:00")
            dusk = ZonedDateTime.parse("2020-01-13T20:00:00+02:00")
        }
        val expected = "SkylightDay.Typical(" +
                "date=${day.date}, dawn=${day.dawn}, sunrise=${day.sunrise}, sunset=${day.sunset}, dusk=${day.dusk}" +
                ")"
        assertEquals(expected, day.toString())
    }
    //endregion

    //region SkylightDay.AlwaysDaytime
    @Test(expected = IllegalStateException::class)
    fun `AlwaysDaytime initializer without date throws exception`() {
        SkylightDay.AlwaysDaytime {}
    }

    @Test fun `AlwaysDaytime with same values are equals`() {
        val day1 = SkylightDay.AlwaysDaytime {
            date = LocalDate.parse("2020-01-13")
        }
        val day2 = SkylightDay.AlwaysDaytime {
            date = LocalDate.parse("2020-01-13")
        }
        assertEquals(day1, day2)
        assertEquals(day1.hashCode(), day2.hashCode())
    }

    @Test fun `AlwaysDaytime with different values are not equals`() {
        val day1 = SkylightDay.AlwaysDaytime {
            date = LocalDate.parse("2020-01-13")
        }
        val day2 = SkylightDay.AlwaysDaytime {
            date = LocalDate.parse("2020-01-14")
        }
        assertNotEquals(day1, day2)
        assertNotEquals(day1.hashCode(), day2.hashCode())
    }

    @Test fun `AlwaysDaytime DSL assigns expected values`() {
        val date = LocalDate.parse("2020-01-13")
        val day = SkylightDay.AlwaysDaytime {
            this.date = date
        }
        assertEquals(date, day.date)
    }

    @Test fun `AlwaysDaytime toString returns value string`() {
        val day = SkylightDay.AlwaysDaytime {
            date = LocalDate.parse("2020-01-13")
        }
        val expected = "SkylightDay.AlwaysDaytime(date=${day.date})"
        assertEquals(expected, day.toString())
    }
    //endregion

    //region SkylightDay.NeverLight
    @Test(expected = IllegalStateException::class)
    fun `NeverLight initializer without date throws exception`() {
        SkylightDay.NeverLight {}
    }

    @Test fun `NeverLight with same values are equals`() {
        val day1 = SkylightDay.NeverLight {
            date = LocalDate.parse("2020-01-13")
        }
        val day2 = SkylightDay.NeverLight {
            date = LocalDate.parse("2020-01-13")
        }
        assertEquals(day1, day2)
        assertEquals(day1.hashCode(), day2.hashCode())
    }

    @Test fun `NeverLight with different values are not equals`() {
        val day1 = SkylightDay.NeverLight {
            date = LocalDate.parse("2020-01-13")
        }
        val day2 = SkylightDay.NeverLight {
            date = LocalDate.parse("2020-01-14")
        }
        assertNotEquals(day1, day2)
        assertNotEquals(day1.hashCode(), day2.hashCode())
    }

    @Test fun `NeverLight DSL assigns expected values`() {
        val date = LocalDate.parse("2020-01-13")
        val day = SkylightDay.NeverLight {
            this.date = date
        }
        assertEquals(date, day.date)
    }

    @Test fun `NeverLight toString returns value string`() {
        val day = SkylightDay.NeverLight {
            date = LocalDate.parse("2020-01-13")
        }
        val expected = "SkylightDay.NeverLight(date=${day.date})"
        assertEquals(expected, day.toString())
    }
    //endregion

    @Test fun `AlwaysDaytime and NeverLight are not equals`() {
        val day1: SkylightDay = SkylightDay.AlwaysDaytime {
            date = LocalDate.parse("2020-01-13")
        }
        val day2: SkylightDay = SkylightDay.NeverLight {
            date = LocalDate.parse("2020-01-13")
        }
        assertNotEquals(day1, day2)
        assertNotEquals(day1.hashCode(), day2.hashCode())
    }
}
