package com.simibubi.create.content.logistics.depot;

import com.simibubi.create.AllClientHandle;
import com.simibubi.create.api.behaviour.movement.MovementBehaviour;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;

public class DepotMovementBehaviour extends MovementBehaviour {
    @Override
    public void tick(MovementContext context) {
        if (context.world.isClientSide()) {
            DepotBehaviour behaviour;
            if (context.temporaryData == null) {
                if (AllClientHandle.INSTANCE.getBlockEntityClientSide(
                    context.contraption,
                    context.localPos
                ) instanceof DepotBlockEntity be) {
                    behaviour = be.depotBehaviour;
                    context.temporaryData = behaviour;
                } else {
                    return;
                }
            } else {
                behaviour = (DepotBehaviour) context.temporaryData;
            }
            TransportedItemStack heldItem = behaviour.heldItem;
            if (heldItem != null) {
                behaviour.tick(heldItem);
            }
        }
    }
}
