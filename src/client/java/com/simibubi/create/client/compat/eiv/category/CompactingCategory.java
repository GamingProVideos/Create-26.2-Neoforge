package com.simibubi.create.client.compat.eiv.category;

import com.simibubi.create.AllItems;
import com.simibubi.create.client.compat.eiv.CreateCategory;
import com.simibubi.create.client.foundation.utility.CreateLang;
import com.simibubi.create.compat.eiv.EivCommonPlugin;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class CompactingCategory extends CreateCategory {
    @Override
    public Component getDisplayName() {
        return CreateLang.translateDirect("recipe.packing");
    }

    @Override
    public int getDisplayHeight() {
        return 70;
    }

    @Override
    public int getSlotCount() {
        return 10;
    }

    @Override
    public Identifier getId() {
        return EivCommonPlugin.PACKING.getId();
    }

    @Override
    public ItemStack getIcon() {
        return AllItems.MECHANICAL_PRESS.getDefaultInstance();
    }

    @Override
    public ItemStack getSubIcon() {
        return AllItems.BASIN.getDefaultInstance();
    }

    @Override
    public List<ItemStack> getCraftReferences() {
        return List.of(AllItems.MECHANICAL_PRESS.getDefaultInstance(), AllItems.BASIN.getDefaultInstance());
    }
}
