package com.simibubi.create.client.content.trains.display;

import com.simibubi.create.client.AllPartialModels;
import com.simibubi.create.client.content.kinetics.base.SingleAxisRotatingVisual;
import com.simibubi.create.client.flywheel.api.visualization.VisualizationContext;
import com.simibubi.create.client.flywheel.lib.model.Models;
import com.simibubi.create.content.trains.display.FlapDisplayBlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class FlapDisplayVisual extends SingleAxisRotatingVisual<FlapDisplayBlockEntity> {
    public FlapDisplayVisual(VisualizationContext context, FlapDisplayBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick, Models.chunkPartial(AllPartialModels.SHAFTLESS_COGWHEEL));
    }

    @Override
    public void setSectionCollector(SectionCollector sectionCollector) {
        switch (blockState.getValue(BlockStateProperties.HORIZONTAL_FACING).getAxis()) {
            case X -> setSectionCollector(sectionCollector, 0, -1, -1, 0, 1, 1);
            case Z -> setSectionCollector(sectionCollector, -1, -1, 0, 1, 1, 0);
        }
    }
}
