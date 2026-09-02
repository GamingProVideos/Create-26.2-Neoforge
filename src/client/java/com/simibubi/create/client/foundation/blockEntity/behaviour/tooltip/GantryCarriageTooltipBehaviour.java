package com.simibubi.create.client.foundation.blockEntity.behaviour.tooltip;

import com.simibubi.create.client.content.contraptions.IDisplayAssemblyExceptions;
import com.simibubi.create.content.contraptions.AssemblyException;
import com.simibubi.create.content.contraptions.gantry.GantryCarriageBlockEntity;

public class GantryCarriageTooltipBehaviour extends TooltipBehaviour<GantryCarriageBlockEntity> implements IDisplayAssemblyExceptions {
    public GantryCarriageTooltipBehaviour(GantryCarriageBlockEntity be) {
        super(be);
    }

    @Override
    public AssemblyException getLastAssemblyException() {
        return blockEntity.getLastAssemblyException();
    }
}
