package com.simibubi.create.client.content.trains.schedule.destination;

import com.google.common.collect.ImmutableList;
import com.simibubi.create.client.content.trains.schedule.IScheduleInput;
import com.simibubi.create.client.foundation.gui.ModularGuiLineBuilder;
import com.simibubi.create.client.foundation.gui.widget.FilterEditBox;
import com.simibubi.create.client.foundation.utility.CreateLang;
import com.simibubi.create.content.trains.schedule.destination.TextScheduleInstruction;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.List;

public abstract class TextScheduleInstructionRender<T extends TextScheduleInstruction> implements IScheduleInput<T> {
    @Override
    public List<Component> getTitleAs(T input, String type) {
        return ImmutableList.of(
            CreateLang.translateDirect("schedule." + type + "." + input.getId().getPath() + ".summary")
                .withStyle(ChatFormatting.GOLD),
            CreateLang.translateDirect("generic.in_quotes", Component.literal(input.getLabelText()))
        );
    }

    @Override
    public void initConfigurationWidgets(T input, ModularGuiLineBuilder builder) {
        builder.addTextInput(0, 121, (e, t) -> modifyEditBox(e), "Text");
    }

    protected void modifyEditBox(FilterEditBox box) {
    }
}
