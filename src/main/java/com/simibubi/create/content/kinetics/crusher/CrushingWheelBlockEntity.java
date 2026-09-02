package com.simibubi.create.content.kinetics.crusher;

import com.simibubi.create.AllAdvancements;
import com.simibubi.create.AllBlockEntityTypes;
import com.simibubi.create.catnip.data.Iterate;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.advancement.CreateTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class CrushingWheelBlockEntity extends KineticBlockEntity {
    public CrushingWheelBlockEntity(BlockPos pos, BlockState state) {
        super(AllBlockEntityTypes.CRUSHING_WHEEL, pos, state);
        setLazyTickRate(20);
    }

    @Override
    public List<CreateTrigger> getAwardables() {
        return List.of(AllAdvancements.CRUSHING_WHEEL, AllAdvancements.CRUSHER_MAXED);
    }

    @Override
    public void onSpeedChanged(float prevSpeed) {
        super.onSpeedChanged(prevSpeed);
        fixControllers();
    }

    public void fixControllers() {
        for (Direction d : Iterate.directions) {
            ((CrushingWheelBlock) getBlockState().getBlock()).updateControllers(
                getBlockState(),
                getLevel(),
                getBlockPos(),
                d
            );
        }
    }

    @Override
    protected AABB createRenderBoundingBox() {
        return new AABB(worldPosition).inflate(1);
    }

    @Override
    public void lazyTick() {
        super.lazyTick();
        fixControllers();
    }
}
