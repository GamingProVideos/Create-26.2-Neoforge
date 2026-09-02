package com.simibubi.create.client.content.trains.schedule.condition;

import com.simibubi.create.catnip.data.Pair;
import com.simibubi.create.client.content.trains.schedule.IScheduleInput;
import com.simibubi.create.client.foundation.utility.CreateLang;
import com.simibubi.create.content.trains.schedule.condition.StationUnloadedCondition;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class StationUnloadedConditionRender implements IScheduleInput<StationUnloadedCondition> {
    @Override
    public Pair<ItemStack, Component> getSummary(StationUnloadedCondition input) {
        return Pair.of(ItemStack.EMPTY, CreateLang.translateDirect("schedule.condition.unloaded"));
    }
}
