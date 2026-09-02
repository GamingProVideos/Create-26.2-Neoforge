package com.simibubi.create.client.foundation.blockEntity.behaviour.tooltip;

import com.simibubi.create.client.content.contraptions.IDisplayAssemblyExceptions;
import com.simibubi.create.content.contraptions.AssemblyException;
import com.simibubi.create.content.contraptions.piston.LinearActuatorBlockEntity;

public class LinearActuatorTooltipBehaviour extends KineticTooltipBehaviour<LinearActuatorBlockEntity> implements IDisplayAssemblyExceptions {
    public LinearActuatorTooltipBehaviour(LinearActuatorBlockEntity be) {
        super(be);
    }

    @Override
    public AssemblyException getLastAssemblyException() {
        return blockEntity.getLastAssemblyException();
    }
}
