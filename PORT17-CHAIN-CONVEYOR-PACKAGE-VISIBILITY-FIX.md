# Port 17 - Chain Conveyor package visibility fix

## Symptom
Packages are packed correctly and accepted by a Chain Conveyor, then immediately appear to disappear.

## Cause
The 26.2 port forces Chain Conveyor rendering through `ChainConveyorRenderer` instead of `ChainConveyorVisual` because the Flywheel OBJ path is unreliable on this port. However, `ChainConveyorClientBehaviour.blockEntityTickBoxVisuals()` still skipped its package-physics tick whenever Flywheel globally reported visualization support. Since the Chain Conveyor visual was no longer registered, no other code initialized/advanced `ChainConveyorPackagePhysicsData`. The BER then rejected packages because `prevPos` stayed null.

## Fix
Always call `tickBoxVisuals()` from the Chain Conveyor client behaviour while the conveyor is using the BER fallback. This restores package position interpolation and makes packages visible while travelling on chains.

Version: `6.0.10-port.17-mc26.2-neoforge`
