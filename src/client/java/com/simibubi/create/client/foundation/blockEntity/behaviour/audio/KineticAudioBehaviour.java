package com.simibubi.create.client.foundation.blockEntity.behaviour.audio;

import com.simibubi.create.client.foundation.sound.SoundScapes;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.gearbox.GearboxBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;

public class KineticAudioBehaviour<T extends KineticBlockEntity> extends AudioBehaviour<T> {
    public KineticAudioBehaviour(T be) {
        super(be);
    }

    @Override
    public void tickAudio() {
        float componentSpeed = Math.abs(blockEntity.getSpeed());
        if (componentSpeed == 0) {
            return;
        }
        float pitch = Mth.clamp(componentSpeed / 256.0f + 0.45f, 0.85f, 1.0f);

        if (blockEntity.isNoisy()) {
            SoundScapes.play(SoundScapes.AmbienceGroup.KINETIC, blockEntity.getBlockPos(), pitch);
        }

        Block block = blockEntity.getBlockState().getBlock();
        if (ICogWheel.isSmallCog(block) || ICogWheel.isLargeCog(block) || block instanceof GearboxBlock) {
            SoundScapes.play(SoundScapes.AmbienceGroup.COG, blockEntity.getBlockPos(), pitch);
        }
    }
}
