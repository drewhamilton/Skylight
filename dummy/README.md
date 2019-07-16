## Skylight: dummy implementation

An implementation that just returns the same set of skylight information regardless of coordinate and date inputs. A
copy of a single `SkylightDay` is always returned with only the `date` field updated based on the input `date` to the
`getSkylightDay` function.
