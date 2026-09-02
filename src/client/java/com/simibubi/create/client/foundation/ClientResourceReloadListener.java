package com.simibubi.create.client.foundation;

import com.simibubi.create.client.Create;
import com.simibubi.create.client.foundation.sound.SoundScapes;
import com.simibubi.create.client.infrastructure.model.TableClothModel;
import com.simibubi.create.content.kinetics.belt.BeltHelper;
import com.simibubi.create.foundation.utility.CreateResourceReloader;
import net.minecraft.server.packs.resources.ResourceManager;

public class ClientResourceReloadListener extends CreateResourceReloader {
    public ClientResourceReloadListener() {
        super("resource");
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        Create.invalidateRenderers();
        SoundScapes.invalidateAll();
        BeltHelper.uprightCache.clear();
        TableClothModel.reload();
    }

}