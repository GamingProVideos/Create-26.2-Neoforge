# Create 6.0.10-port.9 - Client Mixin Classpath Fix

## Fixed startup crash

NeoForge 26.2 could discover `create.client.mixins.json` from the main resource
root but start before Gradle had compiled Create's separate `client` source set.
The JSON was therefore valid and present, while classes such as
`com.simibubi.create.client.mixin.AbstractClientPlayerMixin` did not yet exist
under `build/classes/java/client`.

The result was a PREPARE-phase failure similar to:

```text
InvalidMixinException: The specified mixin
'com.simibubi.create.client.mixin.AbstractClientPlayerMixin' was not found
ClassNotFoundException: com.simibubi.create.client.mixin.AbstractClientPlayerMixin
```

## Port.9 changes

- `runClient`, `runClientData`, and `runGameTestClient` now depend on the
  dedicated `clientClasses` task.
- Added `verifyClientMixinDevOutput` to ensure critical client mixin classes are
  physically present before a development client starts.
- Production `build` also runs the new client output verification.
- Kept the port.8 fix that places `create.client.mixins.json` in
  `src/main/resources`.
- Kept the corrected Create goggles menu button implementation.
- Kept all previous rendering, belt, chain conveyor, water wheel, stress and
  OBJ fixes.

## Build / run

```bat
gradlew.bat clean build
gradlew.bat runClient
```

For IntelliJ, run the Gradle `runClient` configuration after importing the
updated Gradle project so the new task dependency is picked up.
