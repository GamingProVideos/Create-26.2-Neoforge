package com.simibubi.create.client.catnip.render;

import com.simibubi.create.catnip.theme.Color;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public interface ColoredRenderable {

    void render(GuiGraphicsExtractor graphics, int x, int y, Color c);

}
