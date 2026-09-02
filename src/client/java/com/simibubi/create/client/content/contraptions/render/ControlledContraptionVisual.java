package com.simibubi.create.client.content.contraptions.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.client.flywheel.api.visualization.VisualizationContext;
import com.simibubi.create.client.flywheel.lib.transform.TransformStack;
import com.simibubi.create.content.contraptions.ControlledContraptionEntity;
import net.minecraft.core.Direction.Axis;

public class ControlledContraptionVisual extends ContraptionVisual<ControlledContraptionEntity> {
    public ControlledContraptionVisual(
        VisualizationContext ctx,
        ControlledContraptionEntity entity,
        float partialTick
    ) {
        super(ctx, entity, partialTick);
    }

    @Override
    public void transform(PoseStack matrixStack, float partialTicks) {
        Axis axis = entity.getRotationAxis();
        if (axis != null) {
            float angle = entity.getAngle(partialTicks);
            TransformStack.of(matrixStack).nudge(entity.getId()).center().rotateDegrees(angle, axis).uncenter();
        }
    }
}
