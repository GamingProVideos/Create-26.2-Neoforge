package com.simibubi.create.content.contraptions.behaviour.dispenser.storage;

import com.simibubi.create.api.contraption.storage.item.simple.SimpleMountedStorageType;
import net.minecraft.world.Container;

public class DispenserMountedStorageType extends SimpleMountedStorageType<DispenserMountedStorage> {
    public DispenserMountedStorageType() {
        super(DispenserMountedStorage.CODEC);
    }

    @Override
    protected DispenserMountedStorage createStorage(Container handler) {
        return new DispenserMountedStorage(handler);
    }
}
