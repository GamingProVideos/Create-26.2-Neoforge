package com.simibubi.create.content.logistics.packager;

import com.simibubi.create.api.packager.InventoryIdentifier;
import net.minecraft.world.Container;
import org.jspecify.annotations.Nullable;

/**
 * An item inventory, possibly with an associated InventoryIdentifier.
 */
public record IdentifiedInventory(@Nullable InventoryIdentifier identifier, Container handler) {
}
