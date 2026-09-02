package com.simibubi.create;

import com.simibubi.create.api.effect.OpenPipeEffectHandler;
import com.simibubi.create.api.registry.SimpleRegistry;
import com.simibubi.create.impl.effect.*;
import net.minecraft.tags.FluidTags;

public class AllOpenPipeEffectHandlers {
    public static void register() {
        OpenPipeEffectHandler.REGISTRY.registerProvider(SimpleRegistry.Provider.forFluidTag(
            FluidTags.WATER,
            new WaterEffectHandler()
        ));
        OpenPipeEffectHandler.REGISTRY.registerProvider(SimpleRegistry.Provider.forFluidTag(
            FluidTags.LAVA,
            new LavaEffectHandler()
        ));
        OpenPipeEffectHandler.REGISTRY.registerProvider(SimpleRegistry.Provider.forFluidTag(
            AllFluidTags.MILK,
            new MilkEffectHandler()
        ));
        OpenPipeEffectHandler.REGISTRY.register(AllFluids.POTION, new PotionEffectHandler());
        OpenPipeEffectHandler.REGISTRY.register(AllFluids.TEA, new TeaEffectHandler());
    }
}
