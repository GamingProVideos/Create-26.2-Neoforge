# Port.13 - Restore the Create 1.21.1 style main menu

This change replaces the temporary port-only Create menu with a Minecraft 26.2 adaptation of Create 6.0.x's original 1.21.1 menu.

Restored presentation and behaviour:

- Create logo and brass version label
- animated large/small cogwheels on both sides of the logo
- Configure button
- Ponder Index button, disabled on the title screen like upstream
- CurseForge and Modrinth icon buttons
- Support Us and Report Issues buttons
- Return to Menu button
- Create-themed title background using the original Create panorama artwork

Minecraft 26.2 no longer exposes the old arbitrary `PanoramaRenderer(CubeMap)` path used by Create 1.21.1. Its `Panorama` render state is tied to the vanilla cubemap. To avoid replacing Minecraft's global title panorama, the port uses an original Create panorama face as the full-screen Create menu background while preserving the 1.21.1 layout and animated cog presentation.

The title-screen Create goggles button remains texture-backed and does not construct an early ItemStack, preserving the port.11 `Components not bound yet` fix.
