package com.simibubi.create.client.infrastructure.fluid;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.level.material.Fluid;

@FunctionalInterface
public interface FluidTintSource {
    int get(Fluid fluid, DataComponentPatch components);
}
