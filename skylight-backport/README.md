## Skylight: backport

A copy of the interface that uses [ThreeTenBP](https://www.threeten.org/threetenbp/) types instead of Java 8's
`java.time` package, for compatibility down to Java 6.

Note that the `no-tzdb` configuration is provided as a compile- and run-time dependency. Android applications should
explicitly include and initialize [ThreeTenABP](https://github.com/JakeWharton/ThreeTenABP) to get timezone information
at runtime. Non-Android applications should explicitly include the full version of ThreeTenBP as a dependency in order
to get timezone information.
