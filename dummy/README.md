## Skylight: dummy implementation

An implementation that just returns the same set of skylight information regardless of coordinate and date inputs.

### To-do:
* Rather than returning the exact same SkylightDay every time, return a copy with the `date` replaced by the `date`
passed to the `getSkylightDay` call.
