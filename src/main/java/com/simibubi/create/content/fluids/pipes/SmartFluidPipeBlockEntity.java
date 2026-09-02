package com.simibubi.create.content.fluids.pipes;

import com.simibubi.create.AllBlockEntityTypes;
import com.simibubi.create.api.behaviour.BlockEntityBehaviour;
import com.simibubi.create.content.fluids.FluidPropagator;
import com.simibubi.create.content.fluids.pipes.StraightPipeBlockEntity.StraightPipeFluidTransportBehaviour;
import com.simibubi.create.foundation.advancement.CreateTrigger;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.ServerFilteringBehaviour;
import com.simibubi.create.infrastructure.fluids.FluidStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Clearable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class SmartFluidPipeBlockEntity extends SmartBlockEntity implements Clearable {

    private ServerFilteringBehaviour filter;

    public SmartFluidPipeBlockEntity(BlockPos pos, BlockState state) {
        super(AllBlockEntityTypes.SMART_FLUID_PIPE, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour<?>> behaviours) {
        behaviours.add(new SmartPipeBehaviour(this));
        behaviours.add(filter = new ServerFilteringBehaviour(this).forFluids().withCallback(this::onFilterChanged));
    }

    @Override
    public List<CreateTrigger> getAwardables() {
        return FluidPropagator.getSharedTriggers();
    }

    @Override
    public void clearContent() {
        filter.setFilter(ItemStack.EMPTY);
    }

    private void onFilterChanged(ItemStack newFilter) {
        if (!level.isClientSide()) {
            FluidPropagator.propagateChangedPipe(level, worldPosition, getBlockState());
        }
    }

    class SmartPipeBehaviour extends StraightPipeFluidTransportBehaviour {
        public SmartPipeBehaviour(SmartBlockEntity be) {
            super(be);
        }

        @Override
        public boolean canPullFluidFrom(FluidStack fluid, BlockState state, Direction direction) {
            if (fluid.isEmpty() || filter != null && filter.test(fluid)) {
                return super.canPullFluidFrom(fluid, state, direction);
            }
            return false;
        }

        @Override
        public boolean canHaveFlowToward(BlockState state, Direction direction) {
            return state.getBlock() instanceof SmartFluidPipeBlock && SmartFluidPipeBlock.getPipeAxis(state) == direction.getAxis();
        }
    }
}