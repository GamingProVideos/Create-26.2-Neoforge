# Stress, Capacity and Water Wheel Fix

This source includes the following Minecraft 26.2 port repairs:

- Registers `AllEarlyRegistries` through Java's service-provider mechanism.
- Calls `AllBlocks.init()` before `CStress` is constructed, ensuring every
  configured impact and capacity exists even if the early hook is unavailable.
- Restores `CuboidModelMixin`, which is required for Create's
  `neoforge:obj` water wheel and large water wheel models.
- Delegates non-Create custom model loaders to the previous parser, preserving
  Sophisticated Backpacks and Sophisticated Storage custom models.
- Verifies the stress bootstrap, kinetic propagation classes, OBJ parser,
  Creative Motor assets, and both water-wheel model/texture sets are present in
  the production JAR during `gradlew build`.
- Detaches invalid kinetic sources before clearing their stored speed, then
  synchronizes zero speed to clients so shafts cannot keep spinning without
  power.
- Prevents cyclic source references from reviving a disconnected kinetic
  network.
- Rejects a production JAR that still contains the incompatible
  `com.zurrtum.create` namespace.
- Includes an optional `adpother` 26.2 configuration under
  `optional-compatibility/adpother-smoke-only`. It disables carbon, sulfur and
  dust pollution while leaving Advanced Chimneys visual smoke available, and
  corrects the Crushing Wheel Controller activity path for this 26.2 port.
- Replaces obsolete `LivingEntity.baseTick` breathing wrappers with a stable
  Minecraft 26.2 tail injection, preserving diving helmet/backtank underwater
  and lava breathing without the fatal `breatheInLava` mixin loading error.
- Replaces the obsolete `EntityFluidInteraction.update` local capture with a
  stable Minecraft 26.2 tail injection. Create-fluid contact is detected from
  the entity bounding box, avoiding the fatal `MutableBlockPos.getY()` mixin
  loading error while preserving custom-fluid splash behavior.
- Disables the old `RegistryDataLoaderMixin` on NeoForge, leaving
  `DataPackRegistryEvent.NewRegistry` as the single registration path for
  `create:potato_projectile/type`. This prevents the duplicate-registry error
  that blocked world and datapack loading.
- Adds Flywheel visualizer fallbacks for `BlockEntityType` and `EntityType`.
- Registers Create's block-model wrappers during the NeoForge client mod constructor, before Minecraft 26.2 can perform its first block-state model bake. This removes the static "ghost" copy behind animated shafts, cogwheels, large cogwheels, gantry shafts and large water wheels while retaining the animated kinetic model.
  Create's animated renderers now register safely even when Minecraft loads a
  vanilla type before its client extension mixin has transformed that class.

Build on Windows with:

```bat
gradlew.bat clean build
```

Use the JAR created in `build\libs` and remove older Create JARs from the mods
folder before testing.
