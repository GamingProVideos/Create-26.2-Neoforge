package com.simibubi.create.client.foundation.blockEntity.behaviour.audio;

import com.simibubi.create.api.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BehaviourType;

abstract class AudioBehaviour<T extends SmartBlockEntity> extends BlockEntityBehaviour<T> {
    public static final BehaviourType<AudioBehaviour<?>> TYPE = new BehaviourType<>();

    public AudioBehaviour(T be) {
        super(be);
    }

    @Override
    public void tick() {
        tickAudio();
    }

    public abstract void tickAudio();

    @Override
    public BehaviourType<?> getType() {
        return TYPE;
    }
}
