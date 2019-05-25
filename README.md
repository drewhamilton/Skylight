# Skylight

Skylight is a Kotlin interface for providing sunrise, sunset, and other relevant details for a given location and date.

**Heads up!** Pre-release versions up to 0.7.x used the legacy `java.util.Date` type in both parameters and return
values. Starting from 0.8.x, context-appropriate `java.time` types are used, with a separate "backport" interface using
[ThreeTenBP](https://www.threeten.org/threetenbp/) types for Android and other Java 6 consumers. In either case, moving
from 0.7.x to 0.8.x is a significant breaking change.

## Download
[ ![Download](https://api.bintray.com/packages/drewhamilton/Skylight/Skylight/images/download.svg) ](https://bintray.com/drewhamilton/Skylight)

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

Using Skylight with Java 6 and 7 requires a backport, as the primary Skylight interface uses `java.time` types. To use
the backports, which replace `java.time` types with [ThreeTenBP](https://www.threeten.org/threetenbp/), add "backport"
to the group ID:
```groovy
// The base interface, using the ThreeTenBP library:
implementation "drewhamilton.skylight.backport:skylight:$version"

// sunrise-sunset.org implementation:
implementation "drewhamilton.skylight.backport:skylight-sso:$version"
// Calculator implementation:
implementation "drewhamilton.skylight.backport:skylight-calculator:$version"
// Dummy implementation:
implementation "drewhamilton.skylight.backport:skylight-dummy:$version"

// RxJava extensions:
implementation "drewhamilton.skylight.backport:skylight-rx:$version"
```
**Note:** It is not recommended and in some cases impossible to use both Skylight and backported Skylight in a single
app or library.

To use Skylight Views for Android, include this:
```groovy
// Android views and themes:
implementation "drewhamilton.skylight:skylight-views:$version"
```

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
