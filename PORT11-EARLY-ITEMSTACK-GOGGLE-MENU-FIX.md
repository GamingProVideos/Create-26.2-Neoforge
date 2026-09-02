# Port.11 - Minecraft 26.2 early ItemStack / Create menu fix

## Crash

The client reached the title screen and then crashed while rendering `OpenCreateMenuButton` with:

```text
java.lang.NullPointerException: Components not bound yet
...
Item.getDefaultInstance
OpenCreateMenuButton.extractContents
```

Minecraft 26.1+ binds item data-component prototypes during world/data loading. A title-screen UI can render before those prototypes exist, so constructing any `ItemStack` there is unsafe.

## Fix

- `OpenCreateMenuButton` no longer calls `AllItems.GOGGLES.getDefaultInstance()` or `graphics.item(...)`.
- The button now blits `assets/create/textures/item/goggles.png` directly with `RenderPipelines.GUI_TEXTURED`.
- `GoggleConfigScreen` uses the same texture-only path because it can also be opened from the title-screen Create config menu.
- Added Gradle regression checks that reject title-screen ItemStack construction.
- Corrected `Create.VERSION` to `6.0.10-port.11`.

This keeps the Create goggles icon without depending on item component binding.
