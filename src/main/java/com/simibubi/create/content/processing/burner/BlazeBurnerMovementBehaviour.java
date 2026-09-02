package com.simibubi.create.content.processing.burner;

import com.simibubi.create.AllClientHandle;
import com.simibubi.create.api.behaviour.movement.MovementBehaviour;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public class BlazeBurnerMovementBehaviour extends MovementBehaviour {

    @Override
    @Nullable
    public ItemStack canBeDisabledVia(MovementContext context) {
        return null;
    }

    @Override
    public void tick(MovementContext context) {
        if (!context.world.isClientSide()) {
            return;
        }
        AllClientHandle.INSTANCE.tickBlazeBurnerMovement(context);
    }

    public void invalidate(MovementContext context) {
        context.data.remove("Conductor");
    }

    @Override
    public boolean disableBlockEntityRendering() {
        return true;
    }
}
