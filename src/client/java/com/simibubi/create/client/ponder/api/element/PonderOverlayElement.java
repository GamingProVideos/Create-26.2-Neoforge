package com.simibubi.create.client.ponder.api.element;

import com.simibubi.create.client.ponder.foundation.PonderScene;
import com.simibubi.create.client.ponder.foundation.ui.PonderUI;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public interface PonderOverlayElement extends PonderElement {

    void render(PonderScene scene, PonderUI screen, GuiGraphicsExtractor graphics, float partialTicks);

}
