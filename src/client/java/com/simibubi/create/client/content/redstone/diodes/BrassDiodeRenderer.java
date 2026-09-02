package com.simibubi.create.client.content.redstone.diodes;

import com.simibubi.create.catnip.theme.Color;
import com.simibubi.create.client.AllPartialModels;
import com.simibubi.create.client.catnip.render.CachedBuffers;
import com.simibubi.create.client.catnip.render.SuperByteBuffer;
import com.simibubi.create.client.foundation.blockEntity.renderer.ColoredOverlayBlockEntityRenderer;
import com.simibubi.create.content.redstone.diodes.BrassDiodeBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;

public class BrassDiodeRenderer extends ColoredOverlayBlockEntityRenderer<BrassDiodeBlockEntity> {
    public BrassDiodeRenderer(Context context) {
        super(context);
    }

    @Override
    protected int getColor(BrassDiodeBlockEntity be, float partialTicks) {
        return Color.mixColors(0xFF2C0300, 0xFFCD0000, be.getProgress());
    }

    @Override
    protected SuperByteBuffer getOverlayBuffer(BrassDiodeBlockEntity be, ColoredOverlayRenderState state) {
        return CachedBuffers.partial(AllPartialModels.FLEXPEATER_INDICATOR, state.blockState);
    }
}
