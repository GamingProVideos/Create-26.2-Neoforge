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

public class SpoutFillingCategory extends CreateCategory {
    @Override
    public Component getDisplayName() {
        return CreateLang.translateDirect("recipe.spout_filling");
    }

    @Override
    public int getDisplayHeight() {
        return 66;
    }

    @Override
    public int getSlotCount() {
        return 3;
    }

    @Override
    public Identifier getId() {
        return EivCommonPlugin.SPOUT_FILLING.getId();
    }

    @Override
    public ItemStack getIcon() {
        return AllItems.SPOUT.getDefaultInstance();
    }

    @Override
    public ItemStack getSubIcon() {
        return Items.WATER_BUCKET.getDefaultInstance();
    }

    @Override
    public List<ItemStack> getCraftReferences() {
        return List.of(AllItems.SPOUT.getDefaultInstance());
    }
}
