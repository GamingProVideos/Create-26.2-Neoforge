package com.simibubi.create.client.catnip.event;

import com.simibubi.create.client.catnip.lang.LangNumberFormat;
import com.simibubi.create.client.ponder.Ponder;
import com.simibubi.create.foundation.utility.CreateResourceReloader;
import net.minecraft.server.packs.resources.ResourceManager;

public class ClientResourceReloadListener extends CreateResourceReloader {
    public ClientResourceReloadListener() {
        super("ponder");
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        LangNumberFormat.numberFormat.update();
        Ponder.invalidateRenderers();
    }
}
