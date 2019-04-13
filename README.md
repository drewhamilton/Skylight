# Skylight

Skylight is a Kotlin interface for providing sunrise, sunset, and other relevant details for a given location.

## Download
[ ![Download](https://api.bintray.com/packages/drewhamilton/Skylight/Skylight/images/download.svg) ](https://bintray.com/drewhamilton/Skylight)

Skylight is available in JCenter. It is still in development, and the API may undergo breaking changes before version
1.0.0.

To use Skylight, include any of the following in your Gradle dependencies:
```groovy
// The base interface:
implementation "drewhamilton.skylight:skylight:$version"
// RxJava extensions for any Skylight implementation:
implementation "drewhamilton.skylight:skylight-rx:$version"
// sunrise-sunset.org implementation:
implementation "drewhamilton.skylight:skylight-sso:$version"
// Dummy implementation:
implementation "drewhamilton.skylight:skylight-dummy:$version"

// Android views:
implementation "drewhamilton.skylight:skylight-views:$version"
```

## Modules

### `:skylight`
The generic interface itself, designed to be implementation-agnostic.

### `:rx`
RxJava extensions for the Skylight interface.

### `:sso`
An implementation of the interface that uses [sunrise-sunset.org](https://sunrise-sunset.org/)'s
publicly available [API](https://sunrise-sunset.org/api) to determine skylight information. Note that
sunrise-sunset.org's API page says that "You may not use this API in a manner that exceeds reasonable request volume,
constitutes excessive or abusive usage." The same requirement applies to this module of Skylight.

### `:dummy`
An implementation that just returns the same set of skylight information regardless of coordinate and date inputs.

### `:views`
For Android: Skylight themes and a basic card view that can be used to display a skylight event.

### `:app`
A demo app that demonstrates use of the library.

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
