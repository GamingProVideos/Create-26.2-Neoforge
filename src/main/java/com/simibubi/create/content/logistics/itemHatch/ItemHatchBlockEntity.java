package com.simibubi.create.content.logistics.itemHatch;

import com.simibubi.create.AllBlockEntityTypes;
import com.simibubi.create.api.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.ServerFilteringBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Clearable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class ItemHatchBlockEntity extends SmartBlockEntity implements Clearable {

    public ServerFilteringBehaviour filtering;

    public ItemHatchBlockEntity(BlockPos pos, BlockState state) {
        super(AllBlockEntityTypes.ITEM_HATCH, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour<?>> behaviours) {
        behaviours.add(filtering = new ServerFilteringBehaviour(this));
    }

    @Override
    public void clearContent() {
        filtering.setFilter(ItemStack.EMPTY);
    }
}
