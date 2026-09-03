package com.simibubi.create.client.flywheel.impl.visualization.storage;

import com.simibubi.create.client.flywheel.api.visual.BlockEntityVisual;
import com.simibubi.create.client.flywheel.api.visualization.VisualizationContext;
import com.simibubi.create.client.flywheel.impl.FlwImpl;
import com.simibubi.create.client.flywheel.lib.visualization.VisualizationHelper;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jspecify.annotations.Nullable;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BlockEntityStorage extends Storage<BlockEntity> {
    private static final Set<Object> FAILED_VISUAL_TYPES = ConcurrentHashMap.newKeySet();
    private final Long2ObjectMap<BlockEntityVisual<?>> posLookup = new Long2ObjectOpenHashMap<>();

    @Nullable
    public BlockEntityVisual<?> visualAtPos(long pos) {
        return posLookup.get(pos);
    }

    @Override
    public boolean willAccept(BlockEntity blockEntity) {
        if (blockEntity.isRemoved()) {
            return false;
        }

        if (!VisualizationHelper.canVisualize(blockEntity)) {
            return false;
        }

        Level level = blockEntity.getLevel();
        if (level == null) {
            return false;
        }

        if (level.isEmptyBlock(blockEntity.getBlockPos())) {
            return false;
        }

        BlockPos pos = blockEntity.getBlockPos();
        BlockGetter existingChunk = level.getChunkForCollisions(pos.getX() >> 4, pos.getZ() >> 4);
        return existingChunk != null;
    }

    @Override
    @Nullable
    protected BlockEntityVisual<?> createRaw(
        VisualizationContext visualizationContext,
        BlockEntity obj,
        float partialTick
    ) {
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

        BlockEntityVisual<?> visual;
        try {
            visual = visualizer.createVisual(visualizationContext, obj, partialTick);
        } catch (Exception | LinkageError e) {
            // A broken addon visual or a renderer API mismatch must not make the
            // block entity disappear. Leave it unvisualized so the normal 26.2
            // block-entity renderer can take over.
            if (FAILED_VISUAL_TYPES.add(obj.getType())) {
                FlwImpl.LOGGER.error(
                    "Flywheel visual creation failed for block entity type '{}'; falling back to the normal renderer",
                    obj.getType(),
                    e
                );
            }
            return null;
        }

        if (visual == null) {
            return null;
        }

        BlockPos blockPos = obj.getBlockPos();
        posLookup.put(blockPos.asLong(), visual);

        return visual;
    }

    @Override
    public void remove(BlockEntity obj) {
        posLookup.remove(obj.getBlockPos().asLong());
        super.remove(obj);
    }

    @Override
    public void recreateAll(VisualizationContext visualizationContext, float partialTick) {
        posLookup.clear();
        super.recreateAll(visualizationContext, partialTick);
    }

    @Override
    public void invalidate() {
        posLookup.clear();
        super.invalidate();
    }
}
