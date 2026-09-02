# Minecraft 26.2 OBJ kinetic texture fix

## Symptom

The normal shaft/cog ghost-model problem was fixed, but OBJ-based Create models could still render incorrectly. The most visible examples were:

- Chain Conveyor casing/shaft/guard geometry
- Large Water Wheel and Large Water Wheel Extension
- Other Create partials backed by `neoforge:obj` models

The model could animate while its texture orientation/shading looked wrong, especially after a model-state rotation.

## Cause

This port carried its own copied OBJ loader and forced every `loader: neoforge:obj` model through that copy. The copy pre-dated NeoForge's Minecraft 26.2 ModelState fix (NeoForge PR #3355) and only passed a raw transformation matrix into the OBJ baker. That discarded face/UV transformation information. It also bypassed improvements already present in NeoForge 26.2.0.36-beta's native OBJ pipeline.

## Fix in port.4

1. `UnbakedModelParser` now routes `neoforge:obj` directly to NeoForge's native 26.2 `ObjLoader`.
2. Create no longer registers its stale copied `ObjLoader` as an extra Minecraft reload listener.
3. The copied `ObjGeometry` is retained only as fallback/source compatibility, but it now also contains the 26.2 ModelState fix:
   - composes root transforms with `UnbakedElementsHelper.composeRootTransformIntoModelState(...)`;
   - uses `ModelState.inverseFaceTransformation(...)`;
   - applies the inverse face transform to OBJ UV coordinates;
   - uses `Transformation.blockCenterToCorner()` and the 26.2 normal/position transformation helpers.
4. `verifyObjKineticTexturePort` was added to the Gradle build so the stale OBJ routing cannot be accidentally restored.

## Test

Build with Java 25:

```powershell
.\gradlew.bat clean build
```

Then test with only the new Create JAR installed. Check a Chain Conveyor from multiple angles and a Large Water Wheel on X, Y, and Z axes. The animated parts should use the expected Create textures without the broken/rotated OBJ appearance.
