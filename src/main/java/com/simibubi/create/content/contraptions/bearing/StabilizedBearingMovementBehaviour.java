package com.simibubi.create.content.contraptions.bearing;

import com.simibubi.create.api.behaviour.movement.MovementBehaviour;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public class StabilizedBearingMovementBehaviour extends MovementBehaviour {
    @Override
    @Nullable
    public ItemStack canBeDisabledVia(MovementContext context) {
        return null;
    }

    @Override
    public boolean disableBlockEntityRendering() {
        return true;
    }
}
