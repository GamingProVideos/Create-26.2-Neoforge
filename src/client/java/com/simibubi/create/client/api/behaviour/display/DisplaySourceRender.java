package com.simibubi.create.client.api.behaviour.display;

import com.simibubi.create.api.behaviour.display.DisplaySource;
import com.simibubi.create.client.foundation.gui.ModularGuiLineBuilder;
import com.simibubi.create.content.redstone.displayLink.DisplayLinkContext;

public interface DisplaySourceRender {
    void initConfigurationWidgets(
        DisplaySource source,
        DisplayLinkContext context,
        ModularGuiLineBuilder builder,
        boolean isFirstLine
    );
}
