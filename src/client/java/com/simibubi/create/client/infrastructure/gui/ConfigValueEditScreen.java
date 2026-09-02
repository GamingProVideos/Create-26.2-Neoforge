package com.simibubi.create.client.infrastructure.gui;

import com.simibubi.create.catnip.config.ConfigBase;
import com.simibubi.create.catnip.config.FloatValue;
import com.simibubi.create.catnip.config.IntValue;
import com.simibubi.create.client.catnip.gui.AbstractSimiScreen;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/** Text editor used for numeric and string Create config values. */
public class ConfigValueEditScreen extends AbstractSimiScreen {
    private final Screen parent;
    @SuppressWarnings("rawtypes")
    private final ConfigBase.CValue value;
    private EditBox field;
    private String error = "";

    @SuppressWarnings("rawtypes")
    public ConfigValueEditScreen(Screen parent, ConfigBase.CValue value) {
        super(Component.literal("Edit " + value.getName()));
        this.parent = parent;
        this.value = value;
    }

    @Override
    protected void init() {
        setWindowSize(320, 158);
        super.init();
        int x = guiLeft + 25;
        int y = guiTop + 55;

        field = new EditBox(font, x, y, 270, 20, Component.literal(value.getName()));
        field.setMaxLength(128);
        field.setValue(String.valueOf(value.get()));
        field.setFocused(true);
        setFocused(field);
        addRenderableWidget(field);

        addRenderableWidget(Button.builder(Component.literal("Apply"), b -> apply())
            .bounds(guiLeft + 25, guiTop + 103, 82, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Reset"), b -> reset())
            .bounds(guiLeft + 119, guiTop + 103, 82, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Cancel"), b -> minecraft.gui.setScreen(parent))
            .bounds(guiLeft + 213, guiTop + 103, 82, 20).build());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void apply() {
        String text = field.getValue().trim();
        Object current = value.get();
        try {
            Object parsed;
            if (current instanceof Integer) {
                int v = parseInt(text);
                if (value.getRawValue() instanceof IntValue raw) {
                    v = Math.max(raw.min, Math.min(raw.max, v));
                }
                parsed = v;
            } else if (current instanceof Float) {
                float v = Float.parseFloat(text);
                if (value.getRawValue() instanceof FloatValue raw) {
                    v = Math.max(raw.min, Math.min(raw.max, v));
                }
                parsed = v;
            } else if (current instanceof Double) {
                parsed = Double.parseDouble(text);
            } else {
                parsed = text;
            }
            value.set(parsed);
            minecraft.gui.setScreen(parent);
        } catch (RuntimeException ex) {
            error = "Invalid value";
        }
    }

    private int parseInt(String text) {
        if (text.startsWith("#")) {
            return (int) Long.parseLong(text.substring(1), 16);
        }
        return Integer.decode(text);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void reset() {
        value.set(value.getDefault());
        minecraft.gui.setScreen(parent);
    }

    @Override
    protected void renderWindow(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        graphics.fill(guiLeft, guiTop, guiLeft + windowWidth, guiTop + windowHeight, 0xED101010);
        graphics.fill(guiLeft + 1, guiTop + 1, guiLeft + windowWidth - 1, guiTop + windowHeight - 1, 0xED211D17);

        Component title = Component.literal("Edit " + value.getName()).withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD);
        graphics.text(font, title, width / 2 - font.width(title) / 2, guiTop + 14, 0xFFE4BB67, true);

        Component range = rangeText();
        graphics.text(font, range, width / 2 - font.width(range) / 2, guiTop + 34, 0xFF999999, false);

        if (!error.isEmpty()) {
            Component errorText = Component.literal(error).withStyle(ChatFormatting.RED);
            graphics.text(font, errorText, width / 2 - font.width(errorText) / 2, guiTop + 82, 0xFFFF5555, true);
        }
    }

    private Component rangeText() {
        if (value.getRawValue() instanceof IntValue raw) {
            return Component.literal("Range: " + raw.min + " to " + raw.max);
        }
        if (value.getRawValue() instanceof FloatValue raw) {
            return Component.literal("Range: " + raw.min + " to " + raw.max);
        }
        return Component.literal("Default: " + value.getDefault());
    }

    @Override
    public void onClose() {
        minecraft.gui.setScreen(parent);
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }
}
