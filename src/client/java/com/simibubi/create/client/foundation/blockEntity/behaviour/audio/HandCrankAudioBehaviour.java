package com.simibubi.create.client.foundation.blockEntity.behaviour.audio;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.client.catnip.animation.AnimationTickHolder;
import com.simibubi.create.content.kinetics.crank.HandCrankBlockEntity;

public class HandCrankAudioBehaviour extends KineticAudioBehaviour<HandCrankBlockEntity> {
    public HandCrankAudioBehaviour(HandCrankBlockEntity be) {
        super(be);
    }

    @Override
    public void tickAudio() {
        super.tickAudio();
        if (blockEntity.inUse > 0 && AnimationTickHolder.getTicks() % 10 == 0) {
            if (!blockEntity.getBlockState().is(AllBlocks.HAND_CRANK)) {
                return;
            }
            AllSoundEvents.CRANKING.playAt(
                blockEntity.getLevel(),
                blockEntity.getBlockPos(),
                blockEntity.inUse / 2.5f,
                0.65f + (10 - blockEntity.inUse) / 10.0f,
                true
            );
        }
    }
}
