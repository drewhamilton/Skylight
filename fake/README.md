## Skylight: fake implementation

An implementation that returns the same set of skylight information regardless of coordinate and
date inputs. A copy of a single `SkylightDay` is always returned with the `date` field updated
based on the input `date` to the `getSkylightDay` function, and any `Instant` fields adjusted by
adding/subtracting the same number of days.
