package com.simibubi.create.client.api.behaviour.movement;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.client.content.contraptions.render.ActorVisual;
import com.simibubi.create.client.flywheel.api.visualization.VisualizationContext;
import com.simibubi.create.client.foundation.virtualWorld.VirtualRenderWorld;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import net.minecraft.client.gui.Font;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.jspecify.annotations.Nullable;

public interface MovementRenderBehaviour {
    @Nullable
    default MovementRenderState getRenderState(
        Vec3 camera,
        Font textRenderer,
        MovementContext context,
        VirtualRenderWorld renderWorld,
        PoseStack.Pose transform,
        Matrix4f worldMatrix4f
    ) {
        return null;
    }

    @Nullable
    default ActorVisual createVisual(
        VisualizationContext visualizationContext,
        VirtualRenderWorld simulationWorld,
        MovementContext movementContext
    ) {
        return null;
    }
}
