# Minecraft 26.2 kinetic ghost-model fix

## Symptom

When a kinetic block renders, two copies can be visible at the same position: the normal/static block-state model and Create's animated model. It is most obvious on shafts, cogwheels, large cogwheels and large water wheels.

## Cause

Create intentionally wraps several block-state models. In the real level those wrappers suppress the geometry that is rendered separately by Flywheel/the block-entity renderer. On NeoForge 26.2, `AllModels.register()` was only reached from enqueued `FMLClientSetupEvent` work. Minecraft can start its first block-state model load before that queued work has populated `AllModels.ALL`, so `BlockStateModelLoaderMixin` sees no wrapper factory and bakes the ordinary full model. The animated model is then rendered on top of it, producing the stationary ghost copy.

## Fix

`CreateNeoForgeClient` now registers `AllModels` (and forces `AllPartialModels` initialization) immediately in the client mod constructor. `BlockStateModelLoaderMixin` also calls the idempotent `AllModels.register()` at the model-bake interception point as a race-proof fallback. The later client initialization call is therefore safe and cannot double-register the wrapper map.

`verifyKineticModelPort` also checks that early registration remains present and that `ModelBlockRendererMixin` is still included by the native renderer mixin plugin.

## Test

After building port.3, remove the older Create JAR and place only the new JAR in `mods`. Test a shaft, cogwheel, large cogwheel, gearbox shaft faces, gantry shaft and large water wheel. There should be one animated kinetic model, without a stationary duplicate underneath it.
