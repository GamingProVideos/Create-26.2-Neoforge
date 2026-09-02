# Create NeoForge 26.2 source port

This is an independent Minecraft 26.2 / NeoForge 26.2.0.36-beta source port.
It is based on the authorized, publicly available Create-Fly 6.0.9 Minecraft
26.2 source foundation and is labelled `6.0.10-port.5`. It is not an official
Create 6.0.10 release and is not represented as a literal one-to-one copy.

## Requirements

- Minecraft 26.2
- NeoForge 26.2.0.36-beta
- 64-bit Java 25 JDK
- IntelliJ IDEA with Gradle support

## Open and run in IntelliJ

1. Extract the complete ZIP to a short path, for example `C:\Mods\Create262`.
2. In IntelliJ, choose **Open** and select the extracted folder containing
   `settings.gradle`.
3. Select the Java 25 JDK for both the project SDK and Gradle JVM.
4. Let Gradle finish importing.
5. Run the Gradle task `runClient`.

From PowerShell, the equivalent commands are:

```powershell
.\gradlew.bat runClient
.\gradlew.bat build
```

The compiled mod is written to `build\libs`.

## Data generation

NeoForge 26.2 no longer has a run type named `data`. Use `clientData` or
`serverData`. The old `prepareDataRun` task fails because `data` is not a
registered run type in this version.

## Port work

- Updated mappings and signatures for Minecraft 26.2.
- Replaced removed item/fluid capability APIs with NeoForge 26.2 capabilities.
- Ported attachment serialization and client render registration.
- Updated GUI, HUD, entity, block, model, resource-reload, and server-player
  mixins for the 26.2 method layout.
- Preserved the original resources and behaviour available in the permitted
  source foundation.

## Verification

- `compileJava` passes.
- The complete `build` task passes.
- The NeoForge development client reaches the title screen and loads Create
  textures, shaders, sounds, models, and data.


## Port.11 - early ItemStack-safe Create menu

Minecraft 26.2 binds item data-component prototypes during world/data loading. The title screen exists before that point, so constructing `AllItems.GOGGLES.getDefaultInstance()` from the Create menu button caused `NullPointerException: Components not bound yet`. The title/pause Create button and the title-accessible goggle overlay editor now blit `textures/item/goggles.png` directly and do not create an `ItemStack`.
