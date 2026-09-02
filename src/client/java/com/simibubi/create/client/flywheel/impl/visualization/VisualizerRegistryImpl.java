package com.simibubi.create.client.flywheel.impl.visualization;

import com.simibubi.create.client.flywheel.api.visualization.BlockEntityVisualizer;
import com.simibubi.create.client.flywheel.api.visualization.EntityVisualizer;
import com.simibubi.create.client.flywheel.impl.extension.BlockEntityTypeExtension;
import com.simibubi.create.client.flywheel.impl.extension.EntityTypeExtension;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public final class VisualizerRegistryImpl {
    private static final Map<BlockEntityType<?>, BlockEntityVisualizer<?>> BLOCK_ENTITY_VISUALIZERS =
        Collections.synchronizedMap(new IdentityHashMap<>());
    private static final Map<EntityType<?>, EntityVisualizer<?>> ENTITY_VISUALIZERS =
        Collections.synchronizedMap(new IdentityHashMap<>());

    @Nullable
    public static <T extends BlockEntity> BlockEntityVisualizer<? super T> getVisualizer(BlockEntityType<T> type) {
        if (type instanceof BlockEntityTypeExtension<?> extension) {
            return ((BlockEntityTypeExtension<T>) extension).flywheel$getVisualizer();
        }
        return (BlockEntityVisualizer<? super T>) BLOCK_ENTITY_VISUALIZERS.get(type);
    }

    @Nullable
    public static <T extends Entity> EntityVisualizer<? super T> getVisualizer(EntityType<T> type) {
        if (type instanceof EntityTypeExtension<?> extension) {
            return ((EntityTypeExtension<T>) extension).flywheel$getVisualizer();
        }
        return (EntityVisualizer<? super T>) ENTITY_VISUALIZERS.get(type);
    }

    public static <T extends BlockEntity> void setVisualizer(
        BlockEntityType<T> type,
        @Nullable BlockEntityVisualizer<? super T> visualizer
    ) {
        if (type instanceof BlockEntityTypeExtension<?> extension) {
            ((BlockEntityTypeExtension<T>) extension).flywheel$setVisualizer(visualizer);
            return;
        }
        if (visualizer == null) {
            BLOCK_ENTITY_VISUALIZERS.remove(type);
        } else {
            BLOCK_ENTITY_VISUALIZERS.put(type, visualizer);
        }
    }

    public static <T extends Entity> void setVisualizer(
        EntityType<T> type,
        @Nullable EntityVisualizer<? super T> visualizer
    ) {
        if (type instanceof EntityTypeExtension<?> extension) {
            ((EntityTypeExtension<T>) extension).flywheel$setVisualizer(visualizer);
            return;
        }
        if (visualizer == null) {
            ENTITY_VISUALIZERS.remove(type);
        } else {
            ENTITY_VISUALIZERS.put(type, visualizer);
        }
    }

    private VisualizerRegistryImpl() {
    }
}
