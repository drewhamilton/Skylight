# Changelog

## 0.13.2
_2022-12-29_

Fixes a crash in `CalculatorSkylight` in cases where a `SkylightDay` would have a dawn and dusk but
no sunrise or sunset, as in Svalbard on November 13, 2022.

## 0.13.1
_2022-11-27_

Updates `skylight-sunrise-sunset-org`'s transitive okhttp dependency to 4.9.3, because the previous
version had a security vulnerability.

## 0.13.0
_2022-11-11_

Updates the main `Skylight.getSkylightDay` API to a `suspend` function, allowing implementation
classes to implement structured concurrency and callers to not worry about threading.

## 0.12.0
_2020-10-04_

Initial potentially-stable release after a lot of API changes in previous versions. Move publication
from JCenter to Maven Central.

Use an implementation of the `Skylight` interface to get dawn, sunrise, sunset, and dusk times for a
given date and location. Use `CalculatorSkylight` to calculate this locally, use
`SunriseSunsetOrgSkylight` to get the information from
[sunrise-sunset.org](https://sunrise-sunset.org/), or use `FakeSkylight` to fake the information.
