# Create NeoForge 26.2 port.6 render fix

This revision targets three remaining 26.2 client-rendering problems.

## Chain Conveyor

The previous animated partial `chain_conveyor/shaft` is an OBJ containing both the vertical axle and a 2x2 bullwheel face. On this port that face could appear as an oversized top/ghost model. `ChainConveyorRenderer` now renders `AllPartialModels.SHAFT` for the rotating Y-axis axle. The normal chain-conveyor casing stays in the chunk model and chains/guards/packages stay in the block-entity renderer.

## Large Water Wheel

The placed `large_water_wheel` blockstate now resolves to `minecraft:block/air` for every axis/extension state. This only removes the static chunk copy. `WaterWheelRenderer` still builds and animates `AllPartialModels.LARGE_WATER_WHEEL` / `LARGE_WATER_WHEEL_EXTENSION`, so the actual wheel remains visible and spins without a stationary duplicate underneath.

## Belts

`AllBlockEntityRenders` registers belts with `render(...)` instead of the Flywheel visual. `BeltRenderer` always runs its BER geometry path even when Flywheel is globally available, and all belt segments pass `shouldRender`. This is required because uncased belt blockstates intentionally contain only a particle model; without the BER/visual geometry they are invisible.

Version: `6.0.10-port.6`
