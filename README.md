# Skylight
[![Build status](https://travis-ci.org/drewhamilton/Skylight.svg?branch=master)](https://travis-ci.org/drewhamilton/Skylight)

Skylight is a Kotlin interface for providing sunrise, sunset, and similar details for a given location and date.

## Download
[![Download](https://api.bintray.com/packages/drewhamilton/Skylight/Skylight/images/download.svg)](https://bintray.com/drewhamilton/Skylight)

Skylight is available in JCenter. It is still in pre-release development, and the API may undergo breaking changes
before version 1.0.0.

To use Skylight, include any of the following in your Gradle dependencies:
```groovy
// The base interface, using the java.time library:
implementation "drewhamilton.skylight:skylight:$version"

// sunrise-sunset.org implementation:
implementation "drewhamilton.skylight:skylight-sso:$version"
// Calculator implementation:
implementation "drewhamilton.skylight:skylight-calculator:$version"
// Dummy implementation:
implementation "drewhamilton.skylight:skylight-dummy:$version"

// RxJava extensions:
implementation "drewhamilton.skylight:skylight-rx:$version"
```

Skylight requires Java 8. Using Skylight on Android requires enabling
[core library desugaring](https://developer.android.com/studio/preview/features#j8-desugar), available in Android Gradle
Plugin 4.0.0 and higher.

## Usage
Determine and dawn, sunrise, sunset, and dusk for a given location and date, and process it simply and intuitively.
```kotlin
val amsterdam = Coordinates(52.3680, 4.9036)
val tomorrow = LocalDate.now().plusDays(1)
val skylight: Skylight = CalculatorSkylight()

val skylightDay = skylight.getSkylightDay(amsterdam, tomorrow)
val message = when {
    skylightDay is SkylightDay.AlwaysDaytime -> "The sun will be up all day in Amsterdam tomorrow."
    skylightDay is SkylightDay.Typical && skylightDay.sunrise != null -> "The sun will rise tomorrow."
    else -> "The sun will not rise tomorrow."
}
display(message)
```

If you are using Skylight on Android, also see [Skylight Android](https://github.com/drewhamilton/SkylightAndroid) for a
few added features.

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
