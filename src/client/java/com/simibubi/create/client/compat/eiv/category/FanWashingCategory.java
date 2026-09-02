package com.simibubi.create.client.compat.eiv.category;

import com.simibubi.create.AllItems;
import com.simibubi.create.client.compat.eiv.CreateCategory;
import com.simibubi.create.client.foundation.utility.CreateLang;
import com.simibubi.create.compat.eiv.EivCommonPlugin;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

public class FanWashingCategory extends CreateCategory {
    @Override
    public Component getDisplayName() {
        return CreateLang.translateDirect("recipe.fan_washing");
    }

    @Override
    public int getDisplayHeight() {
        return 61;
    }

    @Override
    public int getSlotCount() {
        return 3;
    }

    @Override
    public Identifier getId() {
        return EivCommonPlugin.FAN_WASHING.getId();
    }

    @Override
    public ItemStack getIcon() {
        return AllItems.PROPELLER.getDefaultInstance();
    }

    @Override
    public ItemStack getSubIcon() {
        return Items.WATER_BUCKET.getDefaultInstance();
    }

    @Override
    public List<ItemStack> getCraftReferences() {
        return List.of(AllItems.ENCASED_FAN.getDefaultInstance());
    }
}
