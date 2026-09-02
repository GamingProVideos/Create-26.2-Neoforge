package com.simibubi.create.client.content.redstone.link;

import com.simibubi.create.api.behaviour.BlockEntityBehaviour;
import com.simibubi.create.catnip.data.Couple;
import com.simibubi.create.client.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.client.infrastructure.config.AllConfigs;
import com.simibubi.create.content.redstone.link.RedstoneLinkNetworkHandler;
import com.simibubi.create.content.redstone.link.ServerLinkBehaviour;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BehaviourType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class LinkBehaviour extends BlockEntityBehaviour<SmartBlockEntity> {
    public static final BehaviourType<LinkBehaviour> TYPE = new BehaviourType<>();
    ValueBoxTransform firstSlot;
    ValueBoxTransform secondSlot;
    ServerLinkBehaviour behaviour;

    public LinkBehaviour(SmartBlockEntity be) {
        super(be);
        firstSlot = new RedstoneLinkFrequencySlot(true);
        secondSlot = new RedstoneLinkFrequencySlot(false);
    }

    @Override
    public void initialize() {
        behaviour = blockEntity.getBehaviour(ServerLinkBehaviour.TYPE);
    }

    @Override
    public void tick() {
    }

    @Override
    public void onBehaviourAdded(BehaviourType<?> type, BlockEntityBehaviour<?> behaviour) {
        if (type == ServerLinkBehaviour.TYPE) {
            this.behaviour = (ServerLinkBehaviour) behaviour;
        }
    }

    public void setFrequency(boolean first, ItemStack heldItem) {
        behaviour.setFrequency(first, heldItem);
    }

    public boolean testHit(Boolean first, Vec3 hit) {
        BlockState state = blockEntity.getBlockState();
        Vec3 localHit = hit.subtract(Vec3.atLowerCornerOf(blockEntity.getBlockPos()));
        return (first ? firstSlot : secondSlot).testHit(getLevel(), getPos(), state, localHit);
    }

    public Couple<RedstoneLinkNetworkHandler.Frequency> getNetworkKey() {
        return behaviour.getNetworkKey();
    }

    public ItemStack getFirstStack() {
        return behaviour.frequencyFirst.getStack();
    }

    public ItemStack getLastStack() {
        return behaviour.frequencyLast.getStack();
    }

    @Override
    public BehaviourType<?> getType() {
        return TYPE;
    }

    public float getRenderDistance() {
        return AllConfigs.client().filterItemRenderDistance.getF();
    }
}
