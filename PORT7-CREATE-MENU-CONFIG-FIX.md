# Create 26.2 port.7 - Create menu and config UI restored

This port restores the client-facing Create menu that was missing from the 26.2 source port.

## Restored

- Create goggles button on the Minecraft title menu.
- Create goggles button on the in-game pause menu.
- Existing `mainMenuConfigButtonRow`, `mainMenuConfigButtonOffsetX`, `ingameMenuConfigButtonRow` and `ingameMenuConfigButtonOffsetX` settings are used again.
- Create main menu with Configure, Ponder / Getting Started and Return actions.
- Create configuration root screen.
- Client, Common and Server configuration categories.
- Generic paged editor for the existing JSON-backed `ConfigBase` tree.
- Boolean toggles and enum cycling.
- Numeric/string editing with range clamping and reset-to-default.
- Goggle overlay position editor shortcut.
- NeoForge Mods-list config button via `IConfigScreenFactory`.

The UI edits the same live configuration objects used by the port; it does not create a second config format.
