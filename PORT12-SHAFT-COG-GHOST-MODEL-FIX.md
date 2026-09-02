# Port.12 – Shaft / Cog / Large Cog Ghost Model Fix

Minecraft 26.2 can reach a direct `BlockStateModel.collectParts()` path that bypasses Create's contextual wrapper callback. When that happens the normal baked shaft/cog model is submitted to the chunk **and** Create submits its animated kinetic model, producing the same stationary/animated overlap previously seen on the gearbox/water-wheel path.

## Fix

- `BracketedKineticBlockModel.collectParts()` is now intentionally empty.
- Contextual `addPartsWithInfo()` still exposes the underlying model for `VirtualBlockGetter`, so Create's animated `SuperByteBuffer` / Flywheel model can still be built.
- Brackets still render normally because they are emitted by `addPartsWithInfo()` in a real world.
- `LargeWaterWheelModel` and `GantryShaftModel` receive the same defensive direct-collection guard.

This removes the stationary copy without removing the animated shaft, cogwheel or large cogwheel.
