package dev.drewhamilton.skylight

import java.time.LocalDate
import java.time.ZonedDateTime
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class SkylightDayTest {

    //region SkylightDay.Eventful
    @Test(expected = IllegalArgumentException::class)
    fun `Eventful initializer without any events throws exception`() {
        SkylightDay.Eventful(date = LocalDate.parse("2020-01-13"))
    }

    @Test fun `Eventful with same values are equals`() {
        val day1 = SkylightDay.Eventful(
            date = LocalDate.parse("2020-01-13"),
            dawn = ZonedDateTime.parse("2020-01-13T07:00:00+02:00").toInstant(),
            dusk = ZonedDateTime.parse("2020-01-13T20:00:00+02:00").toInstant()
        )
        val day2 = SkylightDay.Eventful(
            date = LocalDate.parse("2020-01-13"),
            dawn = ZonedDateTime.parse("2020-01-13T07:00:00+02:00").toInstant(),
            dusk = ZonedDateTime.parse("2020-01-13T20:00:00+02:00").toInstant()
        )
        assertEquals(day1, day2)
        assertEquals(day1.hashCode(), day2.hashCode())
    }

    @Test fun `Eventful with different values are not equals`() {
        val day1 = SkylightDay.Eventful(
            date = LocalDate.parse("2020-01-13"),
            sunrise = ZonedDateTime.parse("2020-01-13T07:00:00+02:00").toInstant(),
            sunset = ZonedDateTime.parse("2020-01-13T20:00:00+02:00").toInstant()
        )
        val day2 = SkylightDay.Eventful(
            date = LocalDate.parse("2020-01-13"),
            dawn = ZonedDateTime.parse("2020-01-13T07:00:00+02:00").toInstant(),
            dusk = ZonedDateTime.parse("2020-01-13T20:00:00+02:00").toInstant()
        )
        assertNotEquals(day1, day2)
        assertNotEquals(day1.hashCode(), day2.hashCode())
    }

    @Test fun `Eventful toString returns value string`() {
        val day = SkylightDay.Eventful(
            date = LocalDate.parse("2020-01-13"),
            dawn = ZonedDateTime.parse("2020-01-13T07:00:00+02:00").toInstant(),
            sunrise = ZonedDateTime.parse("2020-01-13T09:00:00+02:00").toInstant(),
            sunset = ZonedDateTime.parse("2020-01-13T18:00:00+02:00").toInstant(),
            dusk = ZonedDateTime.parse("2020-01-13T20:00:00+02:00").toInstant()
        )
        val expected = "Eventful(" +
                "date=${day.date}, dawn=${day.dawn}, sunrise=${day.sunrise}, sunset=${day.sunset}, dusk=${day.dusk}" +
                ")"
        assertEquals(expected, day.toString())
    }
    //endregion

    //region SkylightDay.AlwaysDaytime
    @Test fun `AlwaysDaytime with same values are equals`() {
        val day1 = SkylightDay.AlwaysDaytime(date = LocalDate.parse("2020-01-13"))
        val day2 = SkylightDay.AlwaysDaytime(date = LocalDate.parse("2020-01-13"))
        assertEquals(day1, day2)
        assertEquals(day1.hashCode(), day2.hashCode())
    }

    @Test fun `AlwaysDaytime with different values are not equals`() {
        val day1 = SkylightDay.AlwaysDaytime(date = LocalDate.parse("2020-01-13"))
        val day2 = SkylightDay.AlwaysDaytime(date = LocalDate.parse("2020-01-14"))
        assertNotEquals(day1, day2)
        assertNotEquals(day1.hashCode(), day2.hashCode())
    }

    @Test fun `AlwaysDaytime toString returns value string`() {
        val day = SkylightDay.AlwaysDaytime(date = LocalDate.parse("2020-01-13"))
        val expected = "AlwaysDaytime(date=${day.date})"
        assertEquals(expected, day.toString())
    }
    //endregion

    //region SkylightDay.NeverLight
    @Test fun `NeverLight with same values are equals`() {
        val day1 = SkylightDay.NeverLight(date = LocalDate.parse("2020-01-13"))
        val day2 = SkylightDay.NeverLight(date = LocalDate.parse("2020-01-13"))
        assertEquals(day1, day2)
        assertEquals(day1.hashCode(), day2.hashCode())
    }

    @Test fun `NeverLight with different values are not equals`() {
        val day1 = SkylightDay.NeverLight(date = LocalDate.parse("2020-01-13"))
        val day2 = SkylightDay.NeverLight(date = LocalDate.parse("2020-01-14"))
        assertNotEquals(day1, day2)
        assertNotEquals(day1.hashCode(), day2.hashCode())
    }

    @Test fun `NeverLight toString returns value string`() {
        val day = SkylightDay.NeverLight(date = LocalDate.parse("2020-01-13"))
        val expected = "NeverLight(date=${day.date})"
        assertEquals(expected, day.toString())
    }
    //endregion

    @Test fun `AlwaysDaytime and NeverLight are not equals`() {
        val day1: SkylightDay = SkylightDay.AlwaysDaytime(date = LocalDate.parse("2020-01-13"))
        val day2: SkylightDay = SkylightDay.NeverLight(date = LocalDate.parse("2020-01-13"))
        assertNotEquals(day1, day2)
        assertNotEquals(day1.hashCode(), day2.hashCode())
    }
}
