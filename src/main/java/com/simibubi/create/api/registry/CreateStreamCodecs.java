package com.simibubi.create.api.registry;

import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

/**
 * Network codecs for Create-owned registries.
 *
 * <p>Minecraft/NeoForge 26.2 only permits registry integer-ID codecs for
 * registries participating in registry synchronization. Create has several
 * built-in extension registries that deliberately are not synced that way.
 * Encoding their stable names keeps packets deterministic and also gives
 * source-ported addons a safe codec to use for entries they register.</p>
 */
public final class CreateStreamCodecs {
    private CreateStreamCodecs() {
    }

    public static <T> StreamCodec<RegistryFriendlyByteBuf, T> registryByName(Registry<T> registry) {
        return StreamCodec.of(
            (buffer, value) -> Identifier.STREAM_CODEC.encode(buffer, keyOrThrow(registry, value)),
            buffer -> valueOrThrow(registry, Identifier.STREAM_CODEC.decode(buffer))
        );
    }

    private static <T> Identifier keyOrThrow(Registry<T> registry, T value) {
        Identifier id = registry.getKey(value);
        if (id == null) {
            throw new IllegalArgumentException("Unregistered value in Create registry " + registry.key() + ": " + value);
        }
        return id;
    }

    private static <T> T valueOrThrow(Registry<T> registry, Identifier id) {
        T value = registry.getValue(id);
        if (value == null) {
            throw new IllegalArgumentException("Unknown value '" + id + "' in Create registry " + registry.key());
        }
        return value;
    }
}
