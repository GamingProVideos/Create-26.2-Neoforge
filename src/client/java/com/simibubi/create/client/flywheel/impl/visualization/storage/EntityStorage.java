package com.simibubi.create.client.flywheel.impl.visualization.storage;

import com.simibubi.create.client.flywheel.api.visual.EntityVisual;
import com.simibubi.create.client.flywheel.api.visualization.VisualizationContext;
import com.simibubi.create.client.flywheel.impl.FlwImpl;
import com.simibubi.create.client.flywheel.lib.visualization.VisualizationHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class EntityStorage extends Storage<Entity> {
    private static final Set<Object> FAILED_VISUAL_TYPES = ConcurrentHashMap.newKeySet();
    @Override
    @Nullable
    protected EntityVisual<?> createRaw(VisualizationContext context, Entity obj, float partialTick) {
        var visualizer = VisualizationHelper.getVisualizer(obj);
        if (visualizer == null) {
            return null;
        }

        // Once a visual type has thrown during construction in this session,
        // do not repeatedly throw for every instance in a large factory. The
        // normal Minecraft/Sodium renderer remains available as the fallback.
        if (FAILED_VISUAL_TYPES.contains(obj.getType())) {
            return null;
        }

        try {
            return visualizer.createVisual(context, obj, partialTick);
        } catch (Exception | LinkageError e) {
            if (FAILED_VISUAL_TYPES.add(obj.getType())) {
                FlwImpl.LOGGER.error(
                    "Flywheel visual creation failed for entity type '{}'; falling back to the normal renderer",
                    obj.getType(),
                    e
                );
            }
            return null;
        }
    }

    @Override
    public boolean willAccept(Entity entity) {
        if (!entity.isAlive()) {
            return false;
        }

        if (!VisualizationHelper.canVisualize(entity)) {
            return false;
        }

        Level level = entity.level();
        return level != null;
    }
}
