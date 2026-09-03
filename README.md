# Create 6.0.10 — Minecraft 26.2 NeoForge Port

An unofficial port of **Create 6.0.10** for **Minecraft 26.2** using **NeoForge**.

This project focuses on restoring Create's core gameplay, rendering, logistics, networking, menu systems, Flywheel integration, shader compatibility, and performance on Minecraft 26.2 while keeping behaviour as close as possible to the original Create 1.21.1 release.

> **Current port version:** `6.0.10-port.22-mc26.2-neoforge`

---

## ⚠️ Important

This is an **unofficial development port** of Create.

A large amount of the mod is working, but Minecraft 26.2 introduced major rendering, networking, registry, GUI, and client API changes. Some compatibility problems may still exist.

**Back up your worlds before testing new builds.**

---

## Requirements

- **Minecraft:** 26.2
- **NeoForge:** 26.2.x
- **Java:** 25
- A compatible Minecraft 26.2 NeoForge client/server

Recommended for testing:

- Sodium for Minecraft 26.2
- Iris for Minecraft 26.2 if using shaders

---

# Port Status

A large amount of Create has already been updated and repaired for Minecraft 26.2.

## Working / Fixed

- Create startup and client loading
- Minecraft 26.2 NeoForge compatibility
- Java 25 compatibility
- Create client mixins
- Create configuration screen
- Create 1.21.1-style main menu
- Animated Create menu cogwheels
- Safe early-title-screen rendering
- Shafts
- Cogwheels
- Large Cogwheels
- Gearboxes
- Large Water Wheels
- Belts
- Chain Conveyors
- Dual Chain Conveyor strands
- Chain Conveyor package rendering
- Chain Conveyor package movement
- Chain Conveyor render distance
- Package Port networking
- Package Port target selection
- Package transport networking
- Kinetic ghost-model fixes
- OBJ kinetic texture fixes
- LivingEntity lava/diving mixin compatibility
- Minecraft 26.2 block-entity renderer fallback
- Flywheel failure fallback
- Sodium block-entity compatibility
- Iris shader detection
- Iris shader fallback rendering
- Minecraft 26.2 moving-block render pipelines for contraptions
- Improved Create custom-registry networking
- Several Chain Conveyor performance improvements
- Large-factory Flywheel update batching
- Safer Flywheel worker-task handling
- Additional addon compatibility helpers

---

# Chain Conveyor Fixes

Minecraft 26.2 changed large parts of the block-entity rendering and networking systems used by Create's Chain Conveyor.

## Package Visibility

Packages remain visible while travelling along Chain Conveyors.

Earlier Minecraft 26.2 fallback rendering could leave the server moving a package while the client stopped updating the package's visual physics data.

That has been corrected.

## Render Distance

Long Chain Conveyor connections now use render bounds based on the actual connection instead of only the source conveyor block.

This prevents long chains from disappearing too early when the player moves away from one endpoint.

## Dual Chain Strands

Create Chain Conveyors use **two parallel chain strands** between connected conveyor blocks.

An earlier performance optimization accidentally caused only one strand to render.

**Port 19 restored both strands** while keeping the safe render-distance and performance improvements.

## Package Port Networking

Minecraft 26.2 no longer allows integer-ID synchronization for Create's non-synced custom Package Port target registry.

The port now sends stable registry identifiers instead.

This fixes errors such as:

```text
Cannot use ID syncing for non-synced built-in registry:
create:package_port_target_type
```

---

# Rendering Fixes

Minecraft 26.2 changed several rendering systems Create relied on.

This port contains fixes for:

- duplicated/static Shaft models
- duplicated Cogwheel models
- duplicated Large Cogwheel models
- Gearbox ghost models
- Large Water Wheel ghost models
- invisible Belts
- Chain Conveyor rendering
- Chain Conveyor package rendering
- Chain Conveyor render distance
- kinetic OBJ texture problems
- Flywheel fallback rendering
- block-entity render distance handling
- stationary contraption rendering
- moving contraption block rendering
- shader-enabled contraption rendering

Some Create components intentionally fall back to Minecraft's normal block-entity renderer when the current Flywheel path is unsafe or incompatible.

---

# Flywheel Compatibility

Flywheel required additional compatibility work for Minecraft 26.2.

Port 20 introduced a safer Flywheel fallback system.

If a Flywheel visual cannot be created or updated correctly, Create can fall back to the normal Minecraft renderer instead of leaving the block invisible.

Additional improvements include:

- visual failure recovery
- safer renderer reload handling
- update batching
- reduced duplicate visual updates
- safer worker-task exception handling
- compatibility handling for Sodium
- shader-aware backend selection

This is especially important for very large Create factories where thousands of kinetic block entities may be active at once.

---

# Iris / Shader Compatibility

Shader support has received several dedicated fixes.

## Port 21 — Shader Backend Fallback

When an Iris shader pack is active, Flywheel's instancing and indirect backends are treated as unsafe unless they can correctly participate in the shader pipeline.

The port can switch to Create's normal renderer instead of allowing Create blocks to disappear.

When shaders are turned off, Flywheel can return to its normal backend.

## Port 22 — Moving Block / Contraption Rendering

Minecraft 26.2 provides dedicated render types for moving blocks.

When Iris shaders are active, Create contraption geometry now uses Minecraft's shader-compatible moving-block render paths:

```text
solidMovingBlock
cutoutMovingBlock
translucentMovingBlock
```

This specifically targets problems where:

- Stationary Contraptions existed but their blocks were invisible
- Mechanical contraptions disappeared with shaders enabled
- Create cached moving geometry failed to reach the Iris shader pipeline

Shader compatibility is still an area that may require testing with individual shader packs.

---

# Sodium Compatibility

The port includes compatibility handling for Sodium's block-entity rendering system.

Create/Flywheel visualizers can register and remove Sodium block-entity render predicates correctly.

The goal is to avoid:

- duplicate rendering
- Create blocks disappearing
- Flywheel and Sodium both attempting to own the same block entity
- incorrect fallback behaviour when shaders or Flywheel are disabled

---

# Create Main Menu

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

Minecraft 26.2 changed the panorama and GUI rendering APIs, so some internal implementation details differ from Create 1.21.1.

---

# Networking Fixes

Minecraft 26.2 changed several networking and registry synchronization rules.

This port includes fixes for:

- Package Port target networking
- non-synced Create registries
- stable resource-ID packet encoding
- Item Attribute networking
- safer addon registry packet codecs

A reusable helper is also available for Create-owned registries:

```java
CreateStreamCodecs.registryByName(...)
```

This avoids relying on integer registry synchronization where Minecraft 26.2 does not permit it.

---

# Addon Compatibility

Create addons built for older Minecraft/Create versions are **not automatically binary-compatible** with this port.

Minecraft 26.2 changed:

- Minecraft mappings
- Create APIs
- NeoForge APIs
- rendering APIs
- networking APIs
- registry handling
- client render-state extraction
- data components

Some source compatibility helpers have been restored, including:

```java
Create.ID
Create.asResource(...)
```

However, addons may still require their own Minecraft 26.2 source ports.

---

# Performance

Performance work is being done carefully so optimizations do not break Create's behaviour.

## Chain Conveyor Optimizations

Current improvements include:

- reduced unnecessary package visual ticking
- fewer empty render-state allocations
- reduced work for disconnected conveyors
- improved connection render bounds
- better frustum/render-distance handling
- reduced unnecessary package synchronization
- package update batching
- avoiding unnecessary package-physics processing

## Flywheel / Factory Optimizations

Port 20 added additional work aimed at large factories:

- visual update batching
- reduced duplicate update work
- safer failed-visual handling
- reduced repeated compatibility checks
- cached shader state where appropriate
- safer asynchronous worker-task handling

More profiling and optimization may still be required for extremely large factories.

---

# Building From Source

## Windows

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

# Clean Rebuild

When switching between port versions, a clean rebuild is strongly recommended:

```bat
gradlew.bat clean
gradlew.bat build
```

If you use IntelliJ IDEA, reload the Gradle project after major source-set or build-configuration changes.

---

# Development Notes

This project has required changes for several Minecraft 26.2 API differences, including:

- GUI rendering
- render-state extraction
- block-entity rendering
- moving-block rendering
- shader render pipelines
- resource identifiers
- item data components
- networking codecs
- registry synchronization
- mixin loading
- client source-set handling
- NeoForge mod entry points
- Java 25 mixin compatibility
- Sodium render predicates
- Iris shader detection
- Flywheel backend handling

---

# Known Issues

This is still a development port.

Possible remaining issues include:

- rendering problems on less-tested Create blocks
- shader-pack-specific compatibility problems
- very large factory performance bottlenecks
- networking edge cases not yet encountered
- Create addon compatibility
- addon-specific Flywheel/rendering problems
- missing Minecraft 26.2 API conversions in less-used systems
- occasional behavioural differences from Create 1.21.1

If you find a problem, include:

1. the exact port version
2. `latest.log`
3. the crash report if one was generated
4. a screenshot or video if the issue is visual
5. the shader pack name if shaders are involved
6. whether Sodium and/or Iris are installed
7. clear steps to reproduce the problem

---

# Credits

## Create

Original Create code, project, branding, and assets belong to the **Create development team / Creators-of-Create** and their respective contributors.

This project is a compatibility port and is **not the official Minecraft 26.2 release of Create**.

## Port

Minecraft 26.2 NeoForge port maintained by:

**GamingProVideos / Gamingprovids**

---

# License

Follow all license requirements included with the upstream Create project and any third-party components included in this source distribution.

This port does not remove or replace upstream copyright or license notices.

---

# Version History

## Port 22

- Added Iris-compatible moving-block rendering
- Switched shader-active contraption geometry to Minecraft 26.2 moving-block render types
- Fixed Stationary Contraption blocks disappearing with shaders
- Improved moving contraption compatibility with Iris
- Retained port 21 shader backend fallback

## Port 21

- Added Iris shader-aware Flywheel fallback
- Prevented unsafe Flywheel backends from remaining active with shaders
- Added shader-state change handling
- Allowed Flywheel to recover when shaders are disabled again

## Port 20

- Major stability and compatibility pass
- Added Flywheel visual failure fallback
- Added Flywheel update batching
- Added safer worker-task handling
- Improved Sodium compatibility
- Improved Iris detection
- Hardened custom-registry networking
- Added addon compatibility helpers
- Added `CreateStreamCodecs.registryByName(...)`

## Port 19

- Restored both Chain Conveyor strands
- Kept Chain Conveyor render-distance improvements
- Kept package visibility fixes
- Kept Package Port networking fix
- Kept safe Chain Conveyor performance optimizations

## Port 18

- Chain Conveyor render-distance improvements
- render bounding-box improvements
- Chain Conveyor performance pass
- package update batching

## Port 17

- Fixed packages disappearing while travelling on Chain Conveyors

## Port 16

- Fixed Package Port packet encoding on Minecraft 26.2
- replaced invalid non-synced registry ID networking

## Port 15

- Fixed Create main-menu animated model renderer initialization

## Port 14

- Minecraft 26.2 compile fixes for the Create 1.21.1-style menu

## Port 13

- Restored Create 1.21.1-style main menu

## Port 12

- Fixed Shaft, Cogwheel and Large Cogwheel ghost models

## Port 11

- Fixed early title-screen ItemStack/data-component crash

## Port 10

- Unified client mixin classes into the main development classpath

## Port 9

- Client mixin development-output fixes

## Port 8

- Client mixin resource startup fixes

## Port 7

- Restored Create menu/config integration

## Port 6

- Chain Conveyor, Large Water Wheel and Belt renderer fixes

---

# Reporting Bugs

When reporting a bug, provide as much information as possible.

Useful files include:

```text
run/logs/latest.log
run/crash-reports/<latest crash report>
```

For rendering problems, screenshots are extremely useful.

For shader problems, also include:

```text
Iris version
Sodium version
Shader pack name and version
```

---

# Disclaimer

This project is provided for development and testing.

Back up worlds before use.

Do not assume a world created with a development build will always remain compatible with later port versions.

Use development builds at your own risk.
