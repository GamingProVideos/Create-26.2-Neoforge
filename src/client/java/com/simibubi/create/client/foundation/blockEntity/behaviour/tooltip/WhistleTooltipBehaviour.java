package com.simibubi.create.client.foundation.blockEntity.behaviour.tooltip;

import com.simibubi.create.client.api.goggles.IHaveGoggleInformation;
import com.simibubi.create.client.foundation.utility.CreateLang;
import com.simibubi.create.content.decoration.steamWhistle.WhistleBlockEntity;
import net.minecraft.network.chat.Component;

import java.util.List;

public class WhistleTooltipBehaviour extends TooltipBehaviour<WhistleBlockEntity> implements IHaveGoggleInformation {
    public WhistleTooltipBehaviour(WhistleBlockEntity be) {
        super(be);
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        String[] pitches = CreateLang.translateDirect("generic.notes").getString().split(";");
        CreateLang.translate("generic.pitch", pitches[blockEntity.pitch % pitches.length]).forGoggles(tooltip);
        return true;
    }
}
