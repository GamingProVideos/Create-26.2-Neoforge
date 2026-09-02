package com.simibubi.create.client.content.contraptions.wrench;

import com.simibubi.create.catnip.levelWrappers.WrappedLevel;
import com.simibubi.create.client.flywheel.api.visualization.VisualizationLevel;
import net.minecraft.world.level.Level;

public class NonVisualizationLevel extends WrappedLevel implements VisualizationLevel {
    public NonVisualizationLevel(Level level) {
        super(level);
    }

    @Override
    public boolean supportsVisualization() {
        return false;
    }
}
