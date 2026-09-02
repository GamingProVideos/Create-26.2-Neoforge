# Port 15 - GUI model renderer initialization fix

Minecraft 26.2 can prepare GUI picture-in-picture block previews before Flywheel receives a level renderer reload event. The Create 1.21.1-style menu renders animated cogwheel block previews, so `ModelRenderHelper.INSTANCE` could still be null and crash with `Render Frame`.

## Fix

- Added lazy initialization to `ModelRenderHelper` before shared model consumers are used.
- Corrected the AO helper return/update pairs while touching this code:
  - `getAoCullHelper()` now updates and returns `AO_CULL_INSTANCE`.
  - `getAoHelper()` now updates and returns `AO_INSTANCE`.
- Keeps the 1.21.1-style animated cogwheels instead of removing them.

This directly addresses the crash path through `BlockTransformElementRenderer -> CachedBuffers -> SuperBufferFactory -> ModelRenderHelper.getHelper()`.
