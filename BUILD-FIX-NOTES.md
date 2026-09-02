# Production JAR client-source fix

This project was adjusted after a production startup failure where
`create.client.mixins.json` was present in the Create JAR but client mixin
classes such as these were not:

- `com/simibubi/create/client/mixin/MixinPlugin.class`
- `com/simibubi/create/client/mixin/AbstractClientPlayerMixin.class`

## What changed

- `src/main` remains the common source set.
- `src/client` is now an explicit Gradle `client` source set.
- Both source sets are registered as part of the `create` mod in ModDevGradle.
- The normal JAR task explicitly includes `sourceSets.client.output`.
- The sources JAR includes the client sources too.
- `verifyCreateJar` checks the production JAR for the client mixin config and
  the two classes above.
- `build` depends on `verifyCreateJar`, so CI fails instead of publishing a
  broken JAR if client classes are omitted again.

## Build

Windows:

```bat
gradlew.bat clean build
```

Linux / GitHub Actions:

```bash
./gradlew clean build
```

The normal mod JAR is written to `build/libs/`. Do not use the `-sources.jar`
file in the Minecraft `mods` directory.
