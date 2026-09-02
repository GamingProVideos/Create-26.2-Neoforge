package com.simibubi.create.client.content.trains.schedule.condition;

import com.simibubi.create.catnip.data.Pair;
import com.simibubi.create.client.content.trains.schedule.IScheduleInput;
import com.simibubi.create.client.foundation.utility.CreateLang;
import com.simibubi.create.content.trains.schedule.condition.StationPoweredCondition;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class StationPoweredConditionRender implements IScheduleInput<StationPoweredCondition> {
    @Override
    public Pair<ItemStack, Component> getSummary(StationPoweredCondition input) {
        return Pair.of(ItemStack.EMPTY, CreateLang.translateDirect("schedule.condition.powered"));
    }
}
