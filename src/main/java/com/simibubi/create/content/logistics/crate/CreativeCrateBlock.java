package com.simibubi.create.content.logistics.crate;

import com.simibubi.create.AllBlockEntityTypes;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.infrastructure.items.ItemInventoryProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class CreativeCrateBlock extends CrateBlock implements IBE<CreativeCrateBlockEntity>, ItemInventoryProvider<CreativeCrateBlockEntity> {

    public CreativeCrateBlock(Properties p_i48415_1_) {
        super(p_i48415_1_);
    }

    @Override
    public Container getInventory(
        LevelAccessor world,
        BlockPos pos,
        BlockState state,
        CreativeCrateBlockEntity blockEntity,
        @Nullable Direction context
    ) {
        return blockEntity.inv;
    }

    @Override
    public Class<CreativeCrateBlockEntity> getBlockEntityClass() {
        return CreativeCrateBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends CreativeCrateBlockEntity> getBlockEntityType() {
        return AllBlockEntityTypes.CREATIVE_CRATE;
    }
}
