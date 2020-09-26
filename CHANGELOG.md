# Changelog

## 0.12.0
_2020-09-26_

Initial potentially-stable release after a lot of API changes in previous versions. Move publication
from JCenter to Maven Central.

Use an implementation of the `Skylight` interface to get dawn, sunrise, sunset, and dusk times for a
given date and location. Use `CalculatorSkylight` to calculate this locally, use
`SunriseSunsetOrgSkylight` to get the information from
[sunrise-sunset.org](https://sunrise-sunset.org/), or use `FakeSkylight` to fake the information.
