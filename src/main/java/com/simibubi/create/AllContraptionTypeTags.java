package com.simibubi.create;

import com.simibubi.create.api.contraption.ContraptionType;
import com.simibubi.create.api.registry.CreateRegistryKeys;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;

import static com.simibubi.create.Create.MOD_ID;

public class AllContraptionTypeTags {
    public static final TagKey<ContraptionType> OPENS_CONTROLS = create("opens_controls");
    public static final TagKey<ContraptionType> REQUIRES_VEHICLE_FOR_RENDER = create("requires_vehicle_for_render");

    private static TagKey<ContraptionType> create(String name) {
        return TagKey.create(CreateRegistryKeys.CONTRAPTION_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, name));
    }

    public static void register() {
    }
}
