package com.simibubi.create.client.flywheel.api.backend;

import com.simibubi.create.client.flywheel.api.backend.Engine.CrumblingBlock;
import com.simibubi.create.client.flywheel.api.visualization.VisualizationManager;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import org.joml.Matrix4fc;

import java.util.List;

public interface RenderContext {
    LevelRenderState levelRenderState();

    Matrix4fc projection();

    Matrix4fc viewProjection();

    float partialTick();

    VisualizationManager visualizationManager();

    List<CrumblingBlock> crumblingBlocks();
}
