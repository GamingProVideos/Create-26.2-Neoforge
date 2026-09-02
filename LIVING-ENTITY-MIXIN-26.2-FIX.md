# Minecraft 26.2 LivingEntity mixin startup fix

## Crash fixed

The old Create 6.0.10 callback `breathInLava(...)` used a MixinExtras `@WrapOperation` inside `LivingEntity#baseTick`. Minecraft 26.2 no longer has the invocation layout targeted by that callback, so Mixin reports `0/1 succeeded` and aborts mod loading.

## Port change

`LivingEntityMixin` now uses a stable `@Inject(method = "baseTick()V", at = @At("HEAD"))` callback named `create$tickDivingAir`. It does not capture locals or target an internal invocation.

- Underwater: a working Create diving helmet/backtank restores vanilla air before drowning logic runs.
- Lava: Create's lava breathing/backtank handling runs only when the player's eyes are in lava.
- Creative/invulnerable players are ignored.

The project version was bumped from `6.0.10-port.1` to `6.0.10-port.2` so the corrected JAR has a distinct filename and cannot be confused with an older broken build.

## Build

Windows:

```bat
gradlew.bat clean build
```

Use the normal JAR from `build\libs`. Do **not** put the `-sources.jar` in the Minecraft mods folder. Remove every older Create port JAR before testing the new one.
