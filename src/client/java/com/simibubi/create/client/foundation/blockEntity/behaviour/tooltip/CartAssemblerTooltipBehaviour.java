package com.simibubi.create.client.foundation.blockEntity.behaviour.tooltip;

import com.simibubi.create.client.content.contraptions.IDisplayAssemblyExceptions;
import com.simibubi.create.content.contraptions.AssemblyException;
import com.simibubi.create.content.contraptions.mounted.CartAssemblerBlockEntity;

public class CartAssemblerTooltipBehaviour extends TooltipBehaviour<CartAssemblerBlockEntity> implements IDisplayAssemblyExceptions {
    public CartAssemblerTooltipBehaviour(CartAssemblerBlockEntity be) {
        super(be);
    }

    @Override
    public AssemblyException getLastAssemblyException() {
        return blockEntity.getLastAssemblyException();
    }
}
