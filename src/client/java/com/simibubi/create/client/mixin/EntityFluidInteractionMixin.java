package com.simibubi.create.client.mixin;

import com.simibubi.create.infrastructure.fluids.FlowableFluid;
import com.simibubi.create.infrastructure.fluids.FluidInteractionPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityFluidInteraction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityFluidInteraction.class)
public class EntityFluidInteractionMixin implements FluidInteractionPredicate {
    @Unique
    private boolean inModFluid;

    @Inject(method = "update(Lnet/minecraft/world/entity/Entity;Z)V", at = @At("HEAD"))
    private void clear(Entity entity, boolean ignoreCurrent, CallbackInfo ci) {
        inModFluid = false;
    }

    @Inject(method = "update(Lnet/minecraft/world/entity/Entity;Z)V", at = @At("TAIL"))
    private void update(Entity entity, boolean ignoreCurrent, CallbackInfo ci) {
        Level level = entity.level();
        AABB bounds = entity.getBoundingBox().deflate(1.0E-3D);
        int minX = Mth.floor(bounds.minX);
        int minY = Mth.floor(bounds.minY);
        int minZ = Mth.floor(bounds.minZ);
        int maxX = Mth.floor(bounds.maxX);
        int maxY = Mth.floor(bounds.maxY);
        int maxZ = Mth.floor(bounds.maxZ);
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    FluidState fluidState = level.getFluidState(pos.set(x, y, z));
                    if (fluidState.getType() instanceof FlowableFluid) {
                        inModFluid = true;
                        return;
                    }
                }
            }
        }
    }

    @Override
    public boolean create$inModFluid() {
        return inModFluid;
    }
}
