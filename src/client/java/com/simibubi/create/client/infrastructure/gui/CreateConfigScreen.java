package com.simibubi.create.client.infrastructure.gui;

import com.simibubi.create.client.catnip.gui.AbstractSimiScreen;
import com.simibubi.create.client.content.equipment.goggles.GoggleConfigScreen;
import com.simibubi.create.client.foundation.gui.AllGuiTextures;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.joml.Matrix3x2fStack;

/** Root Create configuration menu. */
public class CreateConfigScreen extends AbstractSimiScreen {
    private final Screen parent;

    public CreateConfigScreen(Screen parent) {
        super(Component.literal("Create Configuration"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        setWindowSize(270, 220);
        super.init();
        int center = width / 2;
        int y = guiTop + 72;

        addRenderableWidget(Button.builder(Component.literal("Client Settings"), b -> minecraft.gui.setScreen(
                new ConfigCategoryScreen(this, com.simibubi.create.client.infrastructure.config.AllConfigs.client(), "Client Settings")))
            .bounds(center - 105, y, 210, 20).build());

        addRenderableWidget(Button.builder(Component.literal("Common / World Generation"), b -> minecraft.gui.setScreen(
                new ConfigCategoryScreen(this, com.simibubi.create.infrastructure.config.AllConfigs.common(), "Common Settings")))
            .bounds(center - 105, y + 24, 210, 20).build());

        addRenderableWidget(Button.builder(Component.literal("Server / Gameplay Settings"), b -> minecraft.gui.setScreen(
                new ConfigCategoryScreen(this, com.simibubi.create.infrastructure.config.AllConfigs.server(), "Server Settings")))
            .bounds(center - 105, y + 48, 210, 20).build());

        Button overlay = Button.builder(Component.literal("Move Goggle Overlay"), b -> minecraft.gui.setScreen(new GoggleConfigScreen(this)))
            .bounds(center - 105, y + 76, 210, 20).build();
        overlay.setTooltip(net.minecraft.client.gui.components.Tooltip.create(
            Component.literal("Click or drag to reposition Create's goggle overlay.")));
        addRenderableWidget(overlay);

        addRenderableWidget(Button.builder(Component.literal("Back"), b -> minecraft.gui.setScreen(parent))
            .bounds(center - 105, y + 124, 210, 20).build());
    }

    @Override
    protected void renderWindow(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        graphics.fill(guiLeft, guiTop, guiLeft + windowWidth, guiTop + windowHeight, 0xE0101010);
        graphics.fill(guiLeft + 1, guiTop + 1, guiLeft + windowWidth - 1, guiTop + windowHeight - 1, 0xE0221E18);

        Matrix3x2fStack pose = graphics.pose();
        pose.pushMatrix();
        pose.translate(width / 2f - 24, guiTop + 4);
        pose.scale(0.1875f, 0.1875f);
        AllGuiTextures.LOGO.render(graphics, 0, 0);
        pose.popMatrix();

        Component title = Component.literal("Create Configuration").withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD);
        graphics.text(font, title, width / 2 - font.width(title) / 2, guiTop + 53, 0xFFE4BB67, true);
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
