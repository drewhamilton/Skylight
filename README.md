# Skylight
[![](https://github.com/drewhamilton/Skylight/workflows/CI/badge.svg?branch=main)](https://github.com/drewhamilton/Skylight/actions?query=workflow%3ACI+branch%3Amain)

Skylight is a Kotlin/JVM interface for determining sunrise, sunset, and similar details for a given
location and date.

## Download
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/dev.drewhamilton.skylight/skylight/badge.svg)](https://maven-badges.herokuapp.com/maven-central/dev.drewhamilton.skylight/skylight)

Skylight is available on Maven Central. To use Skylight, include any of the following in your Gradle
dependencies:

```groovy
// The base interface:
implementation "dev.drewhamilton.skylight:skylight:$version"

// sunrise-sunset.org implementation:
implementation "dev.drewhamilton.skylight:skylight-sunrise-sunset-org:$version"
// Calculator implementation:
implementation "dev.drewhamilton.skylight:skylight-calculator:$version"
// Fake implementation:
implementation "dev.drewhamilton.skylight:skylight-fake:$version"
```

## Usage
Determine dawn, sunrise, sunset, and dusk for a given location and date, and process it simply and
intuitively.

```kotlin
val amsterdam = Coordinates(52.3680, 4.9036)
val tomorrow = LocalDate.now().plusDays(1)
val skylight: Skylight = CalculatorSkylight()

val skylightDay = skylight.getSkylightDay(amsterdam, tomorrow)
val message = when {
    skylightDay is SkylightDay.AlwaysDaytime ->
        "The sun will be up all day in Amsterdam tomorrow."
    skylightDay is SkylightDay.Eventful && skylightDay.sunrise != null ->
        "The sun will rise in Amsterdam tomorrow."
    else ->
        "The sun will not rise in Amsterdam tomorrow."
}
display(message)
```

Skylight requires Java 8.

Using Skylight on Android requires enabling
[core library desugaring](https://developer.android.com/studio/preview/features#j8-desugar),
available with Android Gradle Plugin version 4.0.0 and higher.

Skylight is still in pre-release development, and the API may undergo breaking changes before
version 1.0.0.

## License
```
Copyright 2018 Drew Hamilton

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
