package com.simibubi.create.client.infrastructure.model;

import com.simibubi.create.api.behaviour.BlockEntityBehaviour;
import com.simibubi.create.client.flywheel.lib.model.baked.VirtualBlockGetter;
import com.simibubi.create.content.decoration.bracket.BracketedBlockEntityBehaviour;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class BracketedKineticBlockModel extends WrapperBlockStateModel {
    public BracketedKineticBlockModel(BlockState state, UnbakedRoot unbaked) {
        super(state, unbaked);
    }

    /**
     * Minecraft 26.2 has more than one path that can ask a baked block-state model
     * for its raw parts. The contextual Create hook calls addPartsWithInfo(), but
     * direct collectParts() calls must never expose the underlying kinetic model in
     * the world or it will be drawn as a stationary copy underneath the animated
     * shaft/cog rendered by the block entity visual/renderer.
     *
     * Dynamic Create rendering is unaffected: SuperBufferFactory renders these
     * models through a VirtualBlockGetter, and addPartsWithInfo() deliberately
     * exposes the wrapped model for that virtual render pass.
     */
    @Override
    public void collectParts(RandomSource random, List<BlockStateModelPart> parts) {
        // Intentionally empty. The real rotating geometry is rendered by Create.
    }

    @Override
    public void addPartsWithInfo(
        BlockAndTintGetter world,
        BlockPos pos,
        BlockState state,
        RandomSource random,
        List<BlockStateModelPart> parts
    ) {
        BracketedBlockEntityBehaviour attachmentBehaviour = BlockEntityBehaviour.get(
            world,
            pos,
            BracketedBlockEntityBehaviour.TYPE
        );
        if (attachmentBehaviour == null) {
            addVirtualParts(world, random, parts);
            return;
        }
        BlockState bracket = attachmentBehaviour.getBracket();
        if (bracket == null) {
            addVirtualParts(world, random, parts);
            return;
        }
        Minecraft.getInstance().getModelManager().getBlockStateModelSet().get(bracket).collectParts(random, parts);
    }

    private void addVirtualParts(BlockAndTintGetter world, RandomSource random, List<BlockStateModelPart> parts) {
        if (world instanceof VirtualBlockGetter) {
            model.collectParts(random, parts);
        }
    }
}
