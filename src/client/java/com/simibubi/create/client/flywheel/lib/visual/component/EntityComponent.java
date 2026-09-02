package com.simibubi.create.client.flywheel.lib.visual.component;

import com.simibubi.create.client.flywheel.api.visual.DynamicVisual;

public interface EntityComponent {
    void beginFrame(DynamicVisual.Context context);

    void delete();
}
