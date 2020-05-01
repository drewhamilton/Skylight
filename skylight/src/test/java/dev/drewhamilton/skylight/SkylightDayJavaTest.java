package dev.drewhamilton.skylight;

import org.junit.Test;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SkylightDayJavaTest {

    //region SkylightDay.Typical
    @Test(expected = IllegalStateException.class)
    public void typicalInitializer_withoutDate_throwsException() {
        new SkylightDay.Typical.Builder()
                .setDawn(ZonedDateTime.parse("2020-01-13T07:00:00+02:00"))
                .setSunrise(ZonedDateTime.parse("2020-01-13T09:00:00+02:00"))
                .setSunset(ZonedDateTime.parse("2020-01-13T18:00:00+02:00"))
                .setDusk(ZonedDateTime.parse("2020-01-13T20:00:00+02:00"))
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void typicalInitializer_withoutAnyEvents_throwsException() {
        new SkylightDay.Typical.Builder()
                .setDate(LocalDate.parse("2020-01-13"))
                .build();
    }

    @Test
    public void typical_withSameValues_areEquals() {
        SkylightDay.Typical day1 = new SkylightDay.Typical.Builder()
                .setDate(LocalDate.parse("2020-01-13"))
                .setDawn(ZonedDateTime.parse("2020-01-13T07:00:00+02:00"))
                .setDusk(ZonedDateTime.parse("2020-01-13T20:00:00+02:00"))
                .build();
        SkylightDay.Typical day2 = new SkylightDay.Typical.Builder()
                .setDate(LocalDate.parse("2020-01-13"))
                .setDawn(ZonedDateTime.parse("2020-01-13T07:00:00+02:00"))
                .setDusk(ZonedDateTime.parse("2020-01-13T20:00:00+02:00"))
                .build();
        assertEquals(day1, day2);
        assertEquals(day1.hashCode(), day2.hashCode());
    }

    @Test
    public void typical_withDifferentValues_areNotEquals() {
        SkylightDay.Typical day1 = new SkylightDay.Typical.Builder()
                .setDate(LocalDate.parse("2020-01-13"))
                .setSunrise(ZonedDateTime.parse("2020-01-13T09:00:00+02:00"))
                .setSunset(ZonedDateTime.parse("2020-01-13T18:00:00+02:00"))
                .build();
        SkylightDay.Typical day2 = new SkylightDay.Typical.Builder()
                .setDate(LocalDate.parse("2020-01-13"))
                .setDawn(ZonedDateTime.parse("2020-01-13T07:00:00+02:00"))
                .setDusk(ZonedDateTime.parse("2020-01-13T20:00:00+02:00"))
                .build();
        assertNotEquals(day1, day2);
        assertNotEquals(day1.hashCode(), day2.hashCode());
    }

    @Test
    public void typicalBuilder_assignsExpectedValues() {
        LocalDate date = LocalDate.parse("2020-01-13");
        ZonedDateTime dawn = ZonedDateTime.parse("2020-01-13T07:00:00+02:00");
        ZonedDateTime sunrise = ZonedDateTime.parse("2020-01-13T09:00:00+02:00");
        ZonedDateTime sunset = ZonedDateTime.parse("2020-01-13T18:00:00+02:00");
        ZonedDateTime dusk = ZonedDateTime.parse("2020-01-13T20:00:00+02:00");
        SkylightDay.Typical day = new SkylightDay.Typical.Builder()
                .setDate(date)
                .setDawn(dawn)
                .setSunrise(sunrise)
                .setSunset(sunset)
                .setDusk(dusk)
                .build();
        assertEquals(date, day.getDate());
        assertEquals(dawn, day.getDawn());
        assertEquals(sunrise, day.getSunrise());
        assertEquals(sunset, day.getSunset());
        assertEquals(dusk, day.getDusk());
    }
    //endregion

    //region SkylightDay.AlwaysDaytime
    @Test(expected = IllegalStateException.class)
    public void alwaysDaytimeInitializer_withoutDate_throwsException() {
        new SkylightDay.AlwaysDaytime.Builder().build();
    }

    @Test
    public void alwaysDaytime_withSameValues_areEquals() {
        SkylightDay.AlwaysDaytime day1 = new SkylightDay.AlwaysDaytime.Builder()
                .setDate(LocalDate.parse("2020-01-13"))
                .build();
        SkylightDay.AlwaysDaytime day2 = new SkylightDay.AlwaysDaytime.Builder()
                .setDate(LocalDate.parse("2020-01-13"))
                .build();
        assertEquals(day1, day2);
        assertEquals(day1.hashCode(), day2.hashCode());
    }

    @Test
    public void alwaysDaytime_withDifferentValues_areNotEquals() {
        SkylightDay.AlwaysDaytime day1 = new SkylightDay.AlwaysDaytime.Builder()
                .setDate(LocalDate.parse("2020-01-13"))
                .build();
        SkylightDay.AlwaysDaytime day2 = new SkylightDay.AlwaysDaytime.Builder()
                .setDate(LocalDate.parse("2020-01-14"))
                .build();
        assertNotEquals(day1, day2);
        assertNotEquals(day1.hashCode(), day2.hashCode());
    }

    @Test
    public void alwaysDaytimeBuilder_assignsExpectedValues() {
        LocalDate date = LocalDate.parse("2020-01-13");
        SkylightDay.AlwaysDaytime day = new SkylightDay.AlwaysDaytime.Builder()
                .setDate(date)
                .build();
        assertEquals(date, day.getDate());
    }
    //endregion

    //region SkylightDay.NeverLight
    @Test(expected = IllegalStateException.class)
    public void neverLightInitializer_withoutDate_throwsException() {
        new SkylightDay.NeverLight.Builder().build();
    }

    @Test
    public void neverLight_withSameValues_areEquals() {
        SkylightDay.NeverLight day1 = new SkylightDay.NeverLight.Builder()
                .setDate(LocalDate.parse("2020-01-13"))
                .build();
        SkylightDay.NeverLight day2 = new SkylightDay.NeverLight.Builder()
                .setDate(LocalDate.parse("2020-01-13"))
                .build();
        assertEquals(day1, day2);
        assertEquals(day1.hashCode(), day2.hashCode());
    }

    @Test
    public void neverLight_withDifferentValues_areNotEquals() {
        SkylightDay.NeverLight day1 = new SkylightDay.NeverLight.Builder()
                .setDate(LocalDate.parse("2020-01-13"))
                .build();
        SkylightDay.NeverLight day2 = new SkylightDay.NeverLight.Builder()
                .setDate(LocalDate.parse("2020-01-14"))
                .build();
        assertNotEquals(day1, day2);
        assertNotEquals(day1.hashCode(), day2.hashCode());
    }

    @Test
    public void neverLightBuilder_assignsExpectedValues() {
        LocalDate date = LocalDate.parse("2020-01-13");
        SkylightDay.NeverLight day = new SkylightDay.NeverLight.Builder()
                .setDate(date)
                .build();
        assertEquals(date, day.getDate());
    }
    //endregion
}
