# Port 10 - Unified client classpath fix

Minecraft/NeoForge 26.2 prepares `create.client.mixins.json` before Create can tolerate a missing secondary client output directory. The prior port still kept client Java in a separate Gradle source set, so IDE/dev launches could expose the JSON while `AbstractClientPlayerMixin.class` was not visible to Mixin.

## Fix

- `src/client/java` is now compiled as part of `sourceSets.main`.
- `src/client/resources` is now processed as part of `sourceSets.main`.
- The Create mod dev definition uses only the unified main source set.
- Existing NeoForge/Fabric compatibility exclusions are preserved.
- `verifyClientMixinDevOutput` now checks the **main** output for the critical client mixins.
- Client run tasks depend on that verification.
- The production JAR no longer needs a second client-output merge step.

This specifically targets:

```text
ClassNotFoundException: com.simibubi.create.client.mixin.AbstractClientPlayerMixin
```

After replacing the source, run:

```bat
gradlew.bat clean build
gradlew.bat runClient
```

The expected class is now produced at:

```text
build/classes/java/main/com/simibubi/create/client/mixin/AbstractClientPlayerMixin.class
```
