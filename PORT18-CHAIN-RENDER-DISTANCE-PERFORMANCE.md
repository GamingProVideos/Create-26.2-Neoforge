# Port 18 - Chain Conveyor render-distance + performance pass

This port keeps all fixes through port.17 and adds a targeted performance/culling pass for Chain Conveyors.

## Render distance
- Replaces the fixed 64-block block-entity render box with a tight AABB covering the actual connection endpoints.
- Makes `ChainConveyorRenderer#getViewDistance()` follow Minecraft 26.2's effective chunk render distance (`chunks * 16`), matching the pattern used by vanilla long-distance renderers such as beacons.
- Overrides `shouldRender()` to measure camera distance to the actual chain line segment, not only to the owning conveyor block. This prevents a visible chain span disappearing just because its endpoint moved outside the block-entity distance check.

## Performance
- Empty disconnected conveyors return immediately after normal kinetic ticking.
- Package visual physics is skipped for conveyors with no packages.
- Stationary empty package-position work is skipped.
- Package render-state lists are allocated only when a package is actually renderable.
- Optional render-state fields are reset every extraction so old package/chain states cannot be retained.
- A connection span is rendered once instead of once from each endpoint. Both endpoint guards still render. If the preferred owner is unloaded, the loaded endpoint takes over.
- Re-enables normal frustum culling now that the render AABB correctly covers the complete chain, instead of forcing every conveyor to render off-screen.
- Non-owner endpoints skip the expensive pitch/light/animation setup for the long chain geometry.
- Multiple package transitions on one conveyor are batched into at most one local package-map sync per server tick instead of repeatedly serializing/sending the full maps.
- The cached render bounding box is invalidated immediately when connections are added, removed, cleared, or re-read.

## Build status
Static source checks were run in the ChatGPT environment. A full NeoForge/Gradle build was not executed because the environment does not have the project's Java 25 + Gradle toolchain cached.
