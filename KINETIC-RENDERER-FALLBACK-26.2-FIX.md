# NeoForge 26.2 kinetic renderer fallback fix (port.5)

This patch targets the two remaining visual failures seen in-game:

- Chain Conveyor animated shaft / guard appearing black or incorrectly textured.
- Large Water Wheel animated OBJ becoming very dark/black on parts of the wheel.

## Root cause addressed

The port was still sending these OBJ-backed animated models through the Flywheel visual model path. On 26.2 that path can lose or misapply OBJ material/lighting state. In addition, the port added `CardinalLighting` to the rotating OBJ buffers even though the original Create renderer only applied packed block light. Because the wheel is rotated after that lighting state is selected, faces can become incorrectly dark.

## Changes

1. `AllBlockEntityRenders` now uses the normal block-entity renderer for both Water Wheel types and Chain Conveyor instead of registering their Flywheel visuals.
2. `ChainConveyorRenderer` no longer switches to a partial Flywheel-only branch when visualization is globally enabled; it always renders shaft, guard, chain and packages through the BER.
3. Cardinal-direction lighting was removed from the conveyor shaft/guard and Water Wheel rotating model. Packed block light is retained, matching the older Create renderer behavior.
4. Version bumped to `6.0.10-port.5`.

This is intentionally targeted: other Create kinetic blocks can still use Flywheel.
