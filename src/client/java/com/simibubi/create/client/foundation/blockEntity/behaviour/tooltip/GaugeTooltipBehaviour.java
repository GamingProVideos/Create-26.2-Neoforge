package com.simibubi.create.client.foundation.blockEntity.behaviour.tooltip;

import com.simibubi.create.client.foundation.utility.CreateLang;
import com.simibubi.create.content.kinetics.gauge.GaugeBlockEntity;
import net.minecraft.network.chat.Component;

import java.util.List;

public class GaugeTooltipBehaviour<T extends GaugeBlockEntity> extends KineticTooltipBehaviour<T> {
    public GaugeTooltipBehaviour(T be) {
        super(be);
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        CreateLang.translate("gui.gauge.info_header").forGoggles(tooltip);

        return true;
    }
}
