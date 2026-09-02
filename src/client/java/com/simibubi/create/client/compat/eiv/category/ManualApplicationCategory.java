package com.simibubi.create.client.compat.eiv.category;

import com.simibubi.create.AllItems;
import com.simibubi.create.client.compat.eiv.CreateCategory;
import com.simibubi.create.client.foundation.utility.CreateLang;
import com.simibubi.create.compat.eiv.EivCommonPlugin;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public class ManualApplicationCategory extends CreateCategory {
    @Override
    public Component getDisplayName() {
        return CreateLang.translateDirect("recipe.item_application");
    }

    @Override
    public int getDisplayHeight() {
        return 59;
    }

    @Override
    public int getSlotCount() {
        return 3;
    }

    @Override
    public Identifier getId() {
        return EivCommonPlugin.ITEM_APPLICATION.getId();
    }

    @Override
    public ItemStack getIcon() {
        return AllItems.BRASS_HAND.getDefaultInstance();
    }
}
