# Skylight

Skylight is a Kotlin interface for providing sunrise, sunset, and other relevant details for a given location and date.

**Heads up!** Pre-release versions up to 0.7.x used the legacy `java.util.Date` type in both parameters and return
values. Starting from 0.8.0, context-appropriate `java.time` types are used, with a separate "backport" interface using
[ThreeTenBP](https://www.threeten.org/threetenbp/) types for Android and other Java 6 consumers. In either case, moving
from 0.7.x to 0.8.0 is a significant breaking change.

## Download
[ ![Download](https://api.bintray.com/packages/drewhamilton/Skylight/Skylight/images/download.svg) ](https://bintray.com/drewhamilton/Skylight)

Skylight is available in JCenter. It is still in development, and the API may undergo breaking changes before version
1.0.0.

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

// Android views and themes:
implementation "drewhamilton.skylight:skylight-views:$version"
```

## Modules

### `:skylight`
The generic interface itself, designed to be implementation-agnostic.

### `:backport`
A copy of the interface that uses [ThreeTenBP](https://www.threeten.org/threetenbp/) types instead of Java 8's
`java.time` package, for compatibility down to Java 6.

Note that the `no-tzdb` configuration is provided as a compile- and run-time dependency. Android applications should
explicitly include and initialize [ThreeTenABP](https://github.com/JakeWharton/ThreeTenABP) to get timezone information
at runtime. Non-Android applications should explicitly include the full version of ThreeTenBP as a dependency in order
to get timezone information.

### `:sso`
An implementation that uses [sunrise-sunset.org](https://sunrise-sunset.org/)'s publicly available
[API](https://sunrise-sunset.org/api) to determine skylight information. Note that sunrise-sunset.org's API page says
"You may not use this API in a manner that exceeds reasonable request volume, constitutes excessive or abusive usage."
The same requirement applies to this module of Skylight.

### `:calculator`
An implementation that calculates skylight information locally, using fancy math adapted from AndroidX's (internal)
`TwilightCalculator` class. Much faster than the network implementation.

### `:dummy`
An implementation that just returns the same set of skylight information regardless of coordinate and date inputs.

### `:rx`
RxJava extensions for the Skylight interface.

### `:views`
For Android: Skylight themes and a basic card view that can be used to display a skylight event.

### `:app`
A sample app that demonstrates use of the library.

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
