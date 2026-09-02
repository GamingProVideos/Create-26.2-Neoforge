package com.simibubi.create.client.infrastructure.gui;

import com.simibubi.create.catnip.config.ConfigBase;
import com.simibubi.create.client.catnip.gui.AbstractSimiScreen;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Generic paged editor for Create's JSON-backed ConfigBase tree.
 * This intentionally uses the config objects themselves rather than maintaining
 * a second hard-coded list, so newly ported Create options also appear here.
 */
public class ConfigCategoryScreen extends AbstractSimiScreen {
    private static final int PER_PAGE = 7;

    private final Screen parent;
    private final ConfigBase config;
    private final String displayTitle;
    private final List<Entry> entries = new ArrayList<>();
    private int page;

    public ConfigCategoryScreen(Screen parent, ConfigBase config, String displayTitle) {
        super(Component.literal(displayTitle));
        this.parent = parent;
        this.config = config;
        this.displayTitle = displayTitle;
        discoverEntries();
    }

    private void discoverEntries() {
        entries.clear();
        for (Field field : config.getClass().getFields()) {
            try {
                Object value = field.get(config);
                if (value instanceof ConfigBase child) {
                    entries.add(new Entry(humanize(field.getName()), child, null));
                } else if (value instanceof ConfigBase.CValue<?> cValue && !(value instanceof ConfigBase.ConfigGroup)) {
                    entries.add(new Entry(humanize(cValue.getName()), null, cValue));
                }
            } catch (IllegalAccessException ignored) {
            }
        }
    }

    @Override
    protected void init() {
        setWindowSize(360, 244);
        super.init();
        int start = page * PER_PAGE;
        int end = Math.min(entries.size(), start + PER_PAGE);

        for (int index = start; index < end; index++) {
            Entry entry = entries.get(index);
            int row = index - start;
            int y = guiTop + 38 + row * 25;
            if (entry.child != null) {
                addRenderableWidget(Button.builder(Component.literal("Open..."), b ->
                        minecraft.gui.setScreen(new ConfigCategoryScreen(this, entry.child, entry.label)))
                    .bounds(guiLeft + 215, y, 125, 20).build());
            } else {
                addValueButton(entry, y);
            }
        }

        int pages = Math.max(1, (entries.size() + PER_PAGE - 1) / PER_PAGE);
        Button previous = Button.builder(Component.literal("< Previous"), b -> {
            page = Math.max(0, page - 1);
            minecraft.gui.setScreen(this);
        }).bounds(guiLeft + 20, guiTop + 218, 90, 20).build();
        previous.active = page > 0;
        addRenderableWidget(previous);

        Button next = Button.builder(Component.literal("Next >"), b -> {
            page = Math.min(pages - 1, page + 1);
            minecraft.gui.setScreen(this);
        }).bounds(guiLeft + 250, guiTop + 218, 90, 20).build();
        next.active = page < pages - 1;
        addRenderableWidget(next);

        addRenderableWidget(Button.builder(Component.literal("Back"), b -> minecraft.gui.setScreen(parent))
            .bounds(guiLeft + 130, guiTop + 218, 100, 20).build());
    }

    private void addValueButton(Entry entry, int y) {
        Button[] ref = new Button[1];
        Button button = Button.builder(Component.literal(valueText(entry.value)), b -> handleValue(entry.value, ref[0]))
            .bounds(guiLeft + 215, y, 125, 20).build();
        ref[0] = button;
        addRenderableWidget(button);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void handleValue(ConfigBase.CValue value, Button button) {
        Object current = value.get();
        if (current instanceof Boolean bool) {
            value.set(!bool);
            button.setMessage(Component.literal(valueText(value)));
            return;
        }
        if (current instanceof Enum<?> enumeration) {
            Object[] constants = enumeration.getDeclaringClass().getEnumConstants();
            int index = 0;
            for (int i = 0; i < constants.length; i++) {
                if (constants[i] == current) {
                    index = i;
                    break;
                }
            }
            value.set(constants[(index + 1) % constants.length]);
            button.setMessage(Component.literal(valueText(value)));
            return;
        }
        minecraft.gui.setScreen(new ConfigValueEditScreen(this, value));
    }

    private static String valueText(ConfigBase.CValue<?> value) {
        Object current = value.get();
        if (current instanceof Boolean b) {
            return b ? "ON" : "OFF";
        }
        String text = String.valueOf(current);
        return text.length() > 18 ? text.substring(0, 15) + "..." : text;
    }

    @Override
    protected void renderWindow(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        graphics.fill(guiLeft, guiTop, guiLeft + windowWidth, guiTop + windowHeight, 0xED101010);
        graphics.fill(guiLeft + 1, guiTop + 1, guiLeft + windowWidth - 1, guiTop + windowHeight - 1, 0xED211D17);

        Component title = Component.literal(displayTitle).withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD);
        graphics.text(font, title, width / 2 - font.width(title) / 2, guiTop + 12, 0xFFE4BB67, true);

        int start = page * PER_PAGE;
        int end = Math.min(entries.size(), start + PER_PAGE);
        for (int index = start; index < end; index++) {
            Entry entry = entries.get(index);
            int row = index - start;
            int y = guiTop + 44 + row * 25;
            Component label = Component.literal(entry.label);
            graphics.text(font, label, guiLeft + 20, y, 0xFFE6E0D4, false);
        }

        int pages = Math.max(1, (entries.size() + PER_PAGE - 1) / PER_PAGE);
        Component pageLabel = Component.literal("Page " + (page + 1) + " / " + pages).withStyle(ChatFormatting.DARK_GRAY);
        graphics.text(font, pageLabel, width / 2 - font.width(pageLabel) / 2, guiTop + 204, 0xFF999999, false);
    }

    @Override
    public void onClose() {
        minecraft.gui.setScreen(parent);
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    private static String humanize(String value) {
        String spaced = value.replace('_', ' ').replaceAll("([a-z0-9])([A-Z])", "$1 $2");
        if (spaced.isEmpty()) {
            return value;
        }
        return Character.toUpperCase(spaced.charAt(0)) + spaced.substring(1);
    }

    private record Entry(String label, ConfigBase child, ConfigBase.CValue<?> value) {
    }
}
