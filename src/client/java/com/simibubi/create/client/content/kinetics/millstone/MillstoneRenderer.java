package com.simibubi.create.client.content.kinetics.millstone;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.client.AllPartialModels;
import com.simibubi.create.client.catnip.render.CachedBuffers;
import com.simibubi.create.client.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.client.content.kinetics.base.SingleKineticRenderState;
import com.simibubi.create.client.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer.CrumblingOverlay;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import static com.simibubi.create.client.content.kinetics.base.KineticBlockEntityRenderer.getTintColor;

public class MillstoneRenderer implements BlockEntityRenderer<MillstoneBlockEntity, SingleKineticRenderState> {
    public MillstoneRenderer(Context context) {
    }

    @Override
    public SingleKineticRenderState createRenderState() {
        return new SingleKineticRenderState();
    }

    @Override
    public void extractRenderState(
        MillstoneBlockEntity be,
        SingleKineticRenderState state,
        float partialTicks,
        Vec3 cameraPosition,
        @Nullable CrumblingOverlay breakProgress
    ) {
        Level level = SmartBlockEntityRenderer.extractBase(be, state, breakProgress);
        state.model = CachedBuffers.partial(AllPartialModels.MILLSTONE_COG, state.blockState).cardinalLighting(level)
            .light(state.lightCoords).color(getTintColor(be)).extractRenderState();
        state.angle = KineticBlockEntityRenderer.getRotateAngleWithoutBeOffset(be, state, level);
    }

    @Override
    public void submit(
        SingleKineticRenderState state,
        PoseStack matrices,
        SubmitNodeCollector queue,
        CameraRenderState camera
    ) {
        state.submit(matrices, queue);
    }
}
