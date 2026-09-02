package com.simibubi.create.content.logistics.depot;

import com.simibubi.create.AllBlockEntityTypes;
import com.simibubi.create.api.behaviour.BlockEntityBehaviour;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Clearable;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class DepotBlockEntity extends SmartBlockEntity implements Clearable {

    public DepotBehaviour depotBehaviour;

    public DepotBlockEntity(BlockPos pos, BlockState state) {
        super(AllBlockEntityTypes.DEPOT, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour<?>> behaviours) {
        behaviours.add(depotBehaviour = new DepotBehaviour(this));
        depotBehaviour.addSubBehaviours(behaviours);
    }

    @Override
    public void clearContent() {
        depotBehaviour.clearContent();
    }

    @Nullable
    public TransportedItemStack getHeldItem() {
        return depotBehaviour.heldItem;
    }

    public void setHeldItem(TransportedItemStack item) {
        depotBehaviour.setHeldItem(item);
    }

    public void removeHeldItem() {
        depotBehaviour.removeHeldItem();
    }
}
