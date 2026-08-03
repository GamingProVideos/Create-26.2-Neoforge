<div align="center">

# Create for Minecraft 26.2 — NeoForge

An unofficial community port of Create 6.0.10 to Minecraft 26.2 and NeoForge 26.2.0.36-beta.

[![Minecraft](https://img.shields.io/badge/Minecraft-26.2-62B47A?style=flat)](https://www.minecraft.net/)
[![NeoForge](https://img.shields.io/badge/NeoForge-26.2.0.36--beta-EA5C2B?style=flat)](https://neoforged.net/)
[![Build](https://img.shields.io/badge/build-Gradle-02303A?style=flat&logo=gradle)](#building-and-running)

</div>

> [!IMPORTANT]
> This is an experimental, unofficial port. It is not affiliated with or supported by the official Create, Flywheel,
> Ponder, or NeoForge teams. Please report problems to this repository, not to the upstream projects.

## Project status

| Component | Version |
| --- | --- |
| Minecraft | 26.2 |
| NeoForge | 26.2.0.36-beta |
| Create base | 6.0.10 |
| Port version | 6.0.10-port.1 |
| Java | 25 |

The current Gradle configuration builds a **NeoForge JAR only**. Fabric-related source and metadata are retained where
useful for future loader work, but they are excluded from the current NeoForge build and are not a supported Fabric
release.

## What this port provides

- Create gameplay and assets updated for Minecraft 26.2.
- NeoForge registration, events, attachments, networking, and run configuration updates.
- Rendering updates for newer Minecraft item, entity, block-entity, GUI, and render-pipeline APIs.
- Included porting work for the Create integrations with Flywheel and Ponder.
- Compatibility source for supported recipe-viewer integrations where available.

The goal is to preserve upstream Create behavior as closely as the current Minecraft and NeoForge APIs allow. Some
features may still need additional testing or fixes.

## Before playing

- Back up every world before testing this port.
- Do not use an irreplaceable world or assume that a world can safely be moved back to an older Minecraft version.
- Test complex contraptions, trains, Ponder scenes, shaders, and third-party mod compatibility in a separate world first.
- Shaders may disable Flywheel optimizations or expose rendering incompatibilities.
- Include the full crash report and latest log when reporting a problem.

## Building and running

1. Install a Java 25 JDK.
2. Open the repository as a Gradle project in IntelliJ IDEA.
3. Allow Gradle to download the required Minecraft and NeoForge dependencies.
4. Build the project:

```powershell
.\gradlew.bat clean build
```

The compiled JAR is written to `build/libs`.

Launch a development client with:

```powershell
.\gradlew.bat runClient
```

Available data-generation runs are named `clientData` and `serverData`; there is no run named `data`.

## Editing in IntelliJ IDEA

- Use the Gradle JVM set to Java 25.
- Import `build.gradle` rather than opening only the `src` directory.
- Let the first Gradle sync finish before running or editing generated launch configurations.
- If generated build output becomes inconsistent, close the development client and run `clean build` again.

## Downloads and Maven

This repository does not currently publish an official CurseForge, Modrinth, or Maven artifact for this NeoForge port.
Local development builds are available from `build/libs` after a successful build. Do not use the Create Fly Fabric
Maven coordinates for this NeoForge project.

## Upstream projects and attribution

This port modifies or includes code and assets derived from:

- [Creators-of-Create/Create](https://github.com/Creators-of-Create/Create)
- [Creators-of-Create/Ponder](https://github.com/Creators-of-Create/Ponder)
- [Engine-Room/Flywheel](https://github.com/Engine-Room/Flywheel)
- [ZurrTum/Create Fly](https://www.curseforge.com/minecraft/mc-mods/create-fly)
- Parts of Fabric item-group code
- Parts of NeoForge OBJ-model code

The applicable license notices for included upstream code are stored in the root license file and the `licenses`
directory. Retain those notices when redistributing modified source or compiled builds.

## Contributing

When submitting a fix, state the Minecraft and NeoForge versions used, describe how to reproduce the problem, and
include relevant logs. Keep loader-specific code separated so future NeoForge and Fabric work can be maintained without
mixing client-only code into dedicated-server paths.
