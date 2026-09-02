package com.simibubi.create.content.equipment.potatoCannon;

import com.simibubi.create.api.equipment.potatoCannon.PotatoCannonProjectileType;
import com.simibubi.create.api.registry.CreateRegistryKeys;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import static com.simibubi.create.Create.MOD_ID;

public class AllPotatoProjectileTypes {
    public static final ResourceKey<PotatoCannonProjectileType> FALLBACK = ResourceKey.create(
        CreateRegistryKeys.POTATO_PROJECTILE_TYPE,
        Identifier.fromNamespaceAndPath(MOD_ID, "fallback")
    );
}
