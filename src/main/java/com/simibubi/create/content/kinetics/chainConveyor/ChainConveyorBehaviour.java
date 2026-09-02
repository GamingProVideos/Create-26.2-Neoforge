package com.simibubi.create.content.kinetics.chainConveyor;

import com.simibubi.create.api.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.BehaviourType;

public abstract class ChainConveyorBehaviour extends BlockEntityBehaviour<ChainConveyorBlockEntity> {
    public static final BehaviourType<ChainConveyorBehaviour> TYPE = new BehaviourType<>();

    public ChainConveyorBehaviour(ChainConveyorBlockEntity be) {
        super(be);
    }

    @Override
    public BehaviourType<?> getType() {
        return TYPE;
    }

    public void blockEntityTickBoxVisuals() {
    }

    public void tickBoxVisuals() {
    }

    public void updateChainShapes() {
    }

    public void invalidate() {
    }
}
