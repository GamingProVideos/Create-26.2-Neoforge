package com.simibubi.create.client.infrastructure.gui;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.client.catnip.gui.AbstractSimiScreen;
import com.simibubi.create.client.catnip.gui.element.BoxElement;
import com.simibubi.create.client.catnip.gui.element.GuiGameElement;
import com.simibubi.create.client.foundation.gui.AllGuiTextures;
import com.simibubi.create.client.foundation.utility.CreateLang;
import com.simibubi.create.client.ponder.foundation.ui.PonderTagIndexScreen;
import com.simibubi.create.catnip.theme.Color;
import net.minecraft.ChatFormatting;
import net.minecraft.util.Util;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.joml.Matrix3x2fStack;

/**
 * Minecraft 26.2 port of Create 6.0.x's 1.21.1 main menu.
 *
 * The layout, Create logo, animated cogwheels, platform buttons, support/report
 * buttons, Ponder gating and overall presentation intentionally mirror the
 * original 1.21.1 screen. Minecraft 26.2 replaced the old arbitrary
 * PanoramaRenderer with a render-state panorama tied to vanilla's cubemap, so
 * the Create panorama is displayed as a full-screen Create panorama face rather
 * than replacing the global vanilla cubemap.
 */
public class CreateMainMenuScreen extends AbstractSimiScreen {

    private static final Identifier CREATE_PANORAMA_FACE =
        Identifier.fromNamespaceAndPath(Create.MOD_ID, "textures/gui/title/background/panorama_1.png");

    private static final Component CURSEFORGE_TOOLTIP =
        Component.literal("CurseForge").withStyle(s -> s.withColor(0xFC785C).withBold(true));
    private static final Component MODRINTH_TOOLTIP =
        Component.literal("Modrinth").withStyle(s -> s.withColor(0x3FD32B).withBold(true));

    public static final String CURSEFORGE_LINK = "https://www.curseforge.com/minecraft/mc-mods/create";
    public static final String MODRINTH_LINK = "https://modrinth.com/mod/create";
    public static final String ISSUE_TRACKER_LINK = "https://github.com/Creators-of-Create/Create/issues";
    public static final String SUPPORT_LINK = "https://github.com/Creators-of-Create/Create/wiki/Supporting-the-Project";

    private final Screen parent;
    private Button gettingStarted;
    private long firstRenderTime;

    public CreateMainMenuScreen(Screen parent) {
        super(Component.literal("Create"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        // The original Create menu is a full-screen composition, not a framed
        // config window. Keep the AbstractSimiScreen origin neutral.
        setWindowSize(0, 0);
        super.init();
        addButtons();
    }

    private void addButtons() {
        int yStart = height / 4 + 40;
        int center = width / 2;
        int bHeight = 20;
        int bShortWidth = 98;
        int bLongWidth = 200;

        addRenderableWidget(Button.builder(CreateLang.translateDirect("menu.configure"), b ->
                minecraft.gui.setScreen(new CreateConfigScreen(this)))
            .bounds(center - 100, yStart + 8, bLongWidth, bHeight)
            .build());

        gettingStarted = Button.builder(CreateLang.translateDirect("menu.ponder_index"), b ->
                minecraft.gui.setScreen(new PonderTagIndexScreen()))
            .bounds(center + 2, yStart + 32, bShortWidth, bHeight)
            .build();
        gettingStarted.active = !(parent instanceof TitleScreen) && minecraft.level != null;
        if (!gettingStarted.active) {
            gettingStarted.setTooltip(Tooltip.create(CreateLang.translateDirect("menu.only_ingame")));
        }
        addRenderableWidget(gettingStarted);

        addRenderableWidget(new PlatformIconButton(
            center - 100, yStart + 32, bShortWidth / 2, bHeight,
            AllGuiTextures.CURSEFORGE_LOGO, 0.085f,
            b -> openLink(CURSEFORGE_LINK), Tooltip.create(CURSEFORGE_TOOLTIP)
        ));

        addRenderableWidget(new PlatformIconButton(
            center - 50, yStart + 32, bShortWidth / 2, bHeight,
            AllGuiTextures.MODRINTH_LOGO, 0.0575f,
            b -> openLink(MODRINTH_LINK), Tooltip.create(MODRINTH_TOOLTIP)
        ));

        addRenderableWidget(Button.builder(CreateLang.translateDirect("menu.support"), b -> openLink(SUPPORT_LINK))
            .bounds(center - 100, yStart + 68, bShortWidth, bHeight)
            .build());

        addRenderableWidget(Button.builder(CreateLang.translateDirect("menu.report_bugs"), b -> openLink(ISSUE_TRACKER_LINK))
            .bounds(center + 2, yStart + 68, bShortWidth, bHeight)
            .build());

        addRenderableWidget(Button.builder(CreateLang.translateDirect("menu.return"), b -> minecraft.gui.setScreen(parent))
            .bounds(center - 100, yStart + 92, bLongWidth, bHeight)
            .build());
    }

    @Override
    protected void renderWindowBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        if (parent instanceof TitleScreen) {
            renderCreateBackground(graphics);
        } else {
            extractMenuBackground(graphics);
        }
    }

    private void renderCreateBackground(GuiGraphicsExtractor graphics) {
        // Cover the screen with one of Create 6.0's original panorama faces.
        // This preserves the recognisable 1.21.1 Create menu artwork without
        // hijacking Minecraft 26.2's global vanilla Panorama render state.
        float scale = Math.max(width / 512.0f, height / 512.0f);
        float scaledW = 512 * scale;
        float scaledH = 512 * scale;
        float x = (width - scaledW) / 2.0f;
        float y = (height - scaledH) / 2.0f;

        Matrix3x2fStack pose = graphics.pose();
        pose.pushMatrix();
        pose.translate(x, y);
        pose.scale(scale, scale);
        graphics.blit(RenderPipelines.GUI_TEXTURED, CREATE_PANORAMA_FACE,
            0, 0, 0.0f, 0.0f, 512, 512, 512, 512);
        pose.popMatrix();

        // Same darkening role as the old panorama overlay, keeping the logo and
        // brass-coloured version text readable.
        graphics.fill(0, 0, width, height, 0x50000000);
        graphics.fillGradient(0, 0, width, height, 0x10000000, 0x70000000);
    }

    @Override
    protected void renderWindow(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        if (firstRenderTime == 0L) {
            firstRenderTime = Util.getMillis();
        }

        float elapsed = (Util.getMillis() - firstRenderTime) / 1000.0f;
        float intro = Math.min(1.0f, elapsed);
        int center = width / 2;

        renderAnimatedCogwheels(graphics, center, intro);
        renderLogoAndVersion(graphics, center);
    }

    private void renderAnimatedCogwheels(GuiGraphicsExtractor graphics, int center, float intro) {
        float time = Util.getMillis() / 32.0f;
        float spread = 64 + 28 * intro * intro;

        // The original 1.21.1 screen uses one large and one small cog on each
        // side of the Create logo. GuiGameElement is the 26.2 render-state/PiP
        // replacement for the old immediate-mode block rendering path.
        GuiGameElement.of(AllBlocks.LARGE_COGWHEEL.defaultBlockState())
            .scale(4.0f)
            .rotate(45, time, 0)
            .at(center - spread - 64, 24)
            .render(graphics);

        GuiGameElement.of(AllBlocks.COGWHEEL.defaultBlockState())
            .scale(2.8f)
            .rotate(45, -time * 2.0f + 22.5f, 0)
            .at(center - spread - 22, 58)
            .render(graphics);

        GuiGameElement.of(AllBlocks.LARGE_COGWHEEL.defaultBlockState())
            .scale(4.0f)
            .rotate(45, -time, 0)
            .at(center + spread, 24)
            .render(graphics);

        GuiGameElement.of(AllBlocks.COGWHEEL.defaultBlockState())
            .scale(2.8f)
            .rotate(45, time * 2.0f + 22.5f, 0)
            .at(center + spread - 22, 58)
            .render(graphics);
    }

    private void renderLogoAndVersion(GuiGraphicsExtractor graphics, int center) {
        Matrix3x2fStack pose = graphics.pose();
        pose.pushMatrix();
        pose.translate(center - 32, 32);
        pose.pushMatrix();
        pose.scale(0.25f, 0.25f);
        AllGuiTextures.LOGO.render(graphics, 0, 0);
        pose.popMatrix();

        new BoxElement()
            .withBackground(0x88_000000)
            .flatBorder(new Color(0x01_000000, true))
            .at(-32, 56, 100)
            .withBounds(128, 11)
            .render(graphics);
        pose.popMatrix();

        Component version = Component.literal(Create.NAME)
            .withStyle(ChatFormatting.BOLD)
            .append(Component.literal(" v" + Create.VERSION)
                .withStyle(ChatFormatting.BOLD, ChatFormatting.WHITE));
        graphics.text(font, version, center - font.width(version) / 2, 89, 0xFFE4BB67, true);
    }

    private void openLink(String url) {
        ConfirmLinkScreen.confirmLinkNow(this, url, true);
    }

    @Override
    public void onClose() {
        minecraft.gui.setScreen(parent);
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    private static class PlatformIconButton extends Button {
        private final AllGuiTextures icon;
        private final float iconScale;

        private PlatformIconButton(
            int x,
            int y,
            int width,
            int height,
            AllGuiTextures icon,
            float iconScale,
            OnPress onPress,
            Tooltip tooltip
        ) {
            super(x, y, width, height, CommonComponents.EMPTY, onPress, DEFAULT_NARRATION);
            this.icon = icon;
            this.iconScale = iconScale;
            setTooltip(tooltip);
        }

        @Override
        protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
            extractDefaultSprite(graphics);

            Matrix3x2fStack pose = graphics.pose();
            float iconWidth = icon.getWidth() * iconScale;
            float iconHeight = icon.getHeight() * iconScale;
            pose.pushMatrix();
            pose.translate(
                getX() + getWidth() / 2.0f - iconWidth / 2.0f,
                getY() + getHeight() / 2.0f - iconHeight / 2.0f
            );
            pose.scale(iconScale, iconScale);
            icon.render(graphics, 0, 0);
            pose.popMatrix();
        }
    }
}
