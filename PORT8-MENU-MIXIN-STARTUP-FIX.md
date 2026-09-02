# Create 6.0.10-port.8 - Menu / Mixin Startup Fix

This revision fixes the startup failure seen after port.7 while keeping all port.7 rendering and configuration work.

## Fixed

- Moved `create.client.mixins.json` from `src/client/resources` to `src/main/resources`.
  - NeoForge 26.2 validates mixin configs declared in `META-INF/neoforge.mods.toml` against the main mod resource root during pre-loading.
  - This fixes: `A mixin config named create.client.mixins.json was declared ... but doesn't exist`.
- Fixed `OpenCreateMenuButton` for Minecraft 26.x.
  - Removed the invalid `super.extractContents(...)` call.
  - The custom goggles button now calls `extractDefaultSprite(...)` and renders the goggles item itself.
- Moved Create's `en_us.json` into the main resource root to avoid the huge missing-translation spam during bootstrap and ensure menu/config labels are available early.
- Added a Gradle regression check for the client mixin location, goggles button rendering implementation, and English language file.

## Retained

All previous port.7 fixes remain included, including the Create main/config menu and the kinetic rendering fixes for belts, chain conveyors, shafts, cogwheels, gearboxes and water wheels.
