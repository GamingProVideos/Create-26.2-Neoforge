# Create 6.0.10 — Minecraft 26.2 NeoForge Port

An unofficial port of **Create 6.0.10** for **Minecraft 26.2** using **NeoForge**.

This port focuses on restoring Create's core gameplay, rendering, logistics, menu, and networking behaviour on Minecraft 26.2 while keeping the experience as close as possible to the original Create 1.21.1 version.

> **Current port version:** `6.0.10-port.19-mc26.2-neoforge`

---

## ⚠️ Important

This is an **unofficial port** and is still under active development.

Expect bugs, rendering issues, API incompatibilities, or features that still require additional work.

Always back up your worlds before testing new builds.

---

## Requirements

- **Minecraft:** 26.2
- **NeoForge:** 26.2.x
- **Java:** 25
- A compatible Minecraft 26.2 NeoForge client/server

---

## Port Status

A large amount of Create has already been updated for Minecraft 26.2.

### Working / Fixed

- Create startup and client loading
- Minecraft 26.2 NeoForge compatibility
- Java 25 compatibility
- Create client mixins
- Create configuration screen
- Create-style main menu
- Create 1.21.1-style menu layout
- Animated Create menu cogwheels
- Safe early-menu rendering
- Shafts
- Cogwheels
- Large Cogwheels
- Gearboxes
- Large Water Wheels
- Belts
- Chain Conveyors
- Chain Conveyor package rendering
- Chain Conveyor render distance
- Dual Chain Conveyor strands restored
- Package Port networking
- Package Port target selection
- Package transport on Chain Conveyors
- Kinetic ghost-model fixes
- OBJ kinetic texture fixes
- LivingEntity lava/diving mixin compatibility
- Renderer fallback fixes for Minecraft 26.2
- Several performance improvements for Chain Conveyors

---

## Chain Conveyor Fixes

The Chain Conveyor required several changes because Minecraft 26.2 changed large parts of block-entity rendering and networking.

### Package visibility

Packages now remain visible while travelling along Chain Conveyors.

### Render distance

Long Chain Conveyor connections now use a render area based on the real connection instead of disappearing because the renderer only checked the source block.

### Dual chain strands

Create Chain Conveyors use **two parallel chain strands** between connected conveyor blocks.

An earlier optimization accidentally caused only one strand to render. Port 19 restores both strands while retaining the safe render-distance and performance improvements.

### Package Port networking

Minecraft 26.2 no longer allows registry integer-ID syncing for Create's custom non-synced package target registry.

The port now sends stable registry identifiers instead of attempting invalid registry ID synchronization.

This fixes errors such as:

```text
Cannot use ID syncing for non-synced built-in registry:
create:package_port_target_type
```

---

## Rendering Fixes

Minecraft 26.2 changed several rendering systems used by Create.

This port contains fixes for:

- duplicated/static Shaft models
- duplicated Cogwheel models
- duplicated Large Cogwheel models
- Gearbox ghost models
- Large Water Wheel ghost models
- invisible Belts
- Chain Conveyor rendering
- Chain Conveyor package rendering
- kinetic OBJ texture problems
- Flywheel fallback rendering
- block-entity render distance handling

Some components intentionally use Create's normal block-entity renderer where the current Flywheel path is not yet reliable on Minecraft 26.2.

---

## Create Main Menu

The Create menu has been restored to closely match the **Create 1.21.1** version.

It includes:

- Create logo
- Create version display
- animated cogwheels
- **Configure Create**
- **Ponder / Getting Started**
- project/community links
- return button
- Create-themed menu presentation

Minecraft 26.2 changed the panorama and GUI rendering APIs, so some internal implementation details differ from 1.21.1.

---

## Building From Source

### Windows

Open Command Prompt or PowerShell in the project folder:

```bat
gradlew.bat clean build
```

The built mod should be placed in:

```text
build/libs/
```

To launch the development client:

```bat
gradlew.bat runClient
```

---

## Clean Rebuild

When switching between port versions, a clean rebuild is strongly recommended:

```bat
gradlew.bat clean
gradlew.bat build
```

If you use IntelliJ IDEA, reload the Gradle project after major source-set or build configuration changes.

---

## Development Notes

This project has required changes for several Minecraft 26.2 API differences, including:

- GUI rendering
- block entity rendering
- render state extraction
- resource identifiers
- item data components
- networking codecs
- registry synchronization
- mixin loading
- client source-set handling
- NeoForge mod entry points
- Java 25 mixin compatibility

---

## Known Issues

This is still a development port.

Possible remaining issues include:

- rendering problems on some Create blocks
- Flywheel incompatibilities
- performance problems in very large factories
- networking edge cases
- addon compatibility
- shaders/render-mod compatibility
- missing or incorrect Minecraft 26.2 API ports
- occasional differences from Create 1.21.1 behaviour

If you find a problem, include:

1. the exact port version
2. `latest.log`
3. the crash report if one was generated
4. a screenshot or video if the issue is visual
5. steps to reproduce the problem

---

## Compatibility

Create addons made for older Minecraft/Create versions are **not automatically compatible** with this port.

Addons may need their own Minecraft 26.2 NeoForge ports because Create APIs, Minecraft APIs, mappings, rendering systems, and networking have changed.

---

## Performance

Port 19 includes several Chain Conveyor optimizations such as:

- reduced unnecessary package visual ticking
- fewer empty render-state allocations
- reduced work for disconnected conveyors
- improved connection render bounds
- better frustum/render-distance handling
- reduced unnecessary package synchronization

More optimization work is planned for:

- kinetic block entities
- contraptions
- trains
- package routing
- network traffic
- rendering
- Flywheel integration

---

## Credits

### Create

Original Create project and assets belong to the **Create development team / Creators-of-Create**.

This project is a compatibility port and is not the official Minecraft 26.2 release of Create.

### Port

Minecraft 26.2 NeoForge port maintained by:

**GamingProVideos / Gamingprovids**

---

## License

Follow the license requirements included with the upstream Create project and any licenses included in this source distribution.

This port does not remove or replace upstream copyright or license notices.

---

## Version History

### Port 19

- Restored both Chain Conveyor strands
- Kept Chain Conveyor render-distance improvements
- Kept package visibility fixes
- Kept Package Port networking fix
- Kept safe Chain Conveyor performance optimizations

### Port 18

- Chain Conveyor render-distance improvements
- render bounding-box improvements
- Chain Conveyor performance pass
- package update batching

### Port 17

- Fixed packages disappearing while travelling on Chain Conveyors

### Port 16

- Fixed Package Port packet encoding on Minecraft 26.2
- replaced invalid non-synced registry ID networking

### Port 15

- Fixed Create main-menu animated model renderer initialization

### Port 14

- Minecraft 26.2 compile fixes for the Create 1.21.1-style menu

### Port 13

- Restored Create 1.21.1-style main menu

### Port 12

- Fixed Shaft, Cogwheel and Large Cogwheel ghost models

### Port 11

- Fixed early title-screen ItemStack/data-component crash

### Port 10

- Unified client mixin classes into the main development classpath

### Port 9

- Client mixin development-output fixes

### Port 8

- Client mixin resource startup fixes

### Port 7

- Restored Create menu/config integration

### Port 6

- Chain Conveyor, Large Water Wheel and Belt renderer fixes

---

## Disclaimer

This project is provided for development and testing.

Back up worlds before use.

Do not assume a world created with a development build will always remain compatible with later port versions.
