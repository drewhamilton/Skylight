# Skylight

Skylight is a Kotlin interface for determining sunrise, sunset, and other relevant details ("Skylight information") for
a given location.

## Download
[ ![Download](https://api.bintray.com/packages/drewhamilton/Skylight/Skylight/images/download.svg) ](https://bintray.com/drewhamilton/Skylight)

Skylight is available in JCenter.

To use Skylight in your application, include the following in your app's `build.gradle`:
```groovy
implementation "drewhamilton.skylight:skylight:$version"
```

More modules will be made available in the future.

## Modules

### `:interface`
The generic interface itself is declared here, and is designed to be completely implementation-agnostic.

### `:app`
This module will be an example app that demonstrates the use of the library. It has not yet been implemented.

### `:sso`
The `:sso` module is an implementation of the interface that uses [sunrise-sunset.org](https://sunrise-sunset.org/)'s
publicly available [API](https://sunrise-sunset.org/api) to determine skylight information. Note that
sunrise-sunset.org's API page says that "You may not use this API in a manner that exceeds reasonable request volume,
constitutes excessive or abusive usage." The same requirement applies to the `:sso` module of Skylight.

### `:view`
This module provides a basic card view that can be used to display a skylight event.

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
