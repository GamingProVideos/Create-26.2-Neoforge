package com.simibubi.create.client.infrastructure.gui;

import com.simibubi.create.Create;
import com.simibubi.create.client.infrastructure.config.AllConfigs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.event.ScreenEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Restores Create's goggles button to the vanilla title and pause menus.
 * The configured row/offset values are the same ones already present in CClient.
 */
public class OpenCreateMenuButton extends Button {

    private static final Identifier GOGGLES_ICON =
        Identifier.fromNamespaceAndPath(Create.MOD_ID, "textures/item/goggles.png");

    public OpenCreateMenuButton(int x, int y) {
        super(x, y, 20, 20, CommonComponents.EMPTY, button -> {
            Screen parent = Minecraft.getInstance().gui.screen();
            Minecraft.getInstance().gui.setScreen(new CreateMainMenuScreen(parent));
        }, DEFAULT_NARRATION);
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        // Minecraft 26.1+ does not bind item data-component prototypes until
        // world/data loading. Constructing an ItemStack on the title screen can
        // therefore throw "Components not bound yet". Render the 16x16 goggles
        // texture directly so this button is safe before a world has been opened.
        this.extractDefaultSprite(graphics);
        graphics.blit(RenderPipelines.GUI_TEXTURED, GOGGLES_ICON,
            getX() + 2, getY() + 2, 0, 0, 16, 16, 16, 16);
    }

    public static void onGuiInit(ScreenEvent.Init.Post event) {
        Screen screen = event.getScreen();
        int row;
        int offsetX;
        String[] leftKeys;
        String[] rightKeys;

        if (screen instanceof TitleScreen) {
            row = AllConfigs.client().mainMenuConfigButtonRow.get();
            offsetX = AllConfigs.client().mainMenuConfigButtonOffsetX.get();
            leftKeys = new String[]{"menu.singleplayer", "menu.multiplayer", "fml.menu.mods", "narrator.button.language"};
            rightKeys = new String[]{"menu.singleplayer", "menu.multiplayer", "menu.online", "narrator.button.accessibility"};
        } else if (screen instanceof PauseScreen) {
            row = AllConfigs.client().ingameMenuConfigButtonRow.get();
            offsetX = AllConfigs.client().ingameMenuConfigButtonOffsetX.get();
            leftKeys = new String[]{"menu.returnToGame", "gui.advancements", "menu.sendFeedback", "menu.options", "menu.returnToMenu"};
            rightKeys = new String[]{"menu.returnToGame", "gui.stats", "menu.reportBugs", "menu.shareToLan", "menu.returnToMenu"};
        } else {
            return;
        }

        if (row <= 0) {
            return;
        }

        boolean left = offsetX < 0;
        AbstractWidget target = findByText(event.getListenersList(), row, left ? leftKeys : rightKeys);
        if (target == null) {
            target = findByRow(event.getListenersList(), row, left);
        }
        if (target == null) {
            return;
        }

        int x = target.getX() + offsetX + (left ? -20 : target.getWidth());
        event.addListener(new OpenCreateMenuButton(x, target.getY()));
    }

    private static AbstractWidget findByText(List<GuiEventListener> listeners, int row, String[] keys) {
        if (row > keys.length) {
            return null;
        }
        String wanted = Component.translatable(keys[row - 1]).getString();
        for (GuiEventListener listener : listeners) {
            if (listener instanceof AbstractWidget widget && widget.getMessage().getString().equals(wanted)) {
                return widget;
            }
        }
        return null;
    }

    /**
     * Fallback for menu layouts whose translation keys changed in newer Minecraft versions.
     */
    private static AbstractWidget findByRow(List<GuiEventListener> listeners, int row, boolean left) {
        List<AbstractWidget> widgets = new ArrayList<>();
        for (GuiEventListener listener : listeners) {
            if (listener instanceof AbstractWidget widget && widget.visible && widget.getWidth() >= 80 && widget.getHeight() >= 18) {
                widgets.add(widget);
            }
        }
        widgets.sort(Comparator.comparingInt(AbstractWidget::getY).thenComparingInt(AbstractWidget::getX));

        List<Integer> ys = widgets.stream().map(AbstractWidget::getY).distinct().toList();
        if (row > ys.size()) {
            return null;
        }
        int targetY = ys.get(row - 1);
        return widgets.stream().filter(w -> w.getY() == targetY)
            .min(left ? Comparator.comparingInt(AbstractWidget::getX)
                : Comparator.comparingInt(AbstractWidget::getX).reversed())
            .orElse(null);
    }
}
