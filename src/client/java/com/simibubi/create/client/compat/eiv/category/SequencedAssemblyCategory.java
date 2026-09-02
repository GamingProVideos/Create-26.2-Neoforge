package com.simibubi.create.client.compat.eiv.category;

import com.simibubi.create.AllItems;
import com.simibubi.create.client.compat.eiv.CreateCategory;
import com.simibubi.create.client.foundation.utility.CreateLang;
import com.simibubi.create.compat.eiv.EivCommonPlugin;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public class SequencedAssemblyCategory extends CreateCategory {
    @Override
    public Component getDisplayName() {
        return CreateLang.translateDirect("recipe.sequenced_assembly");
    }

    @Override
    public int getDisplayHeight() {
        return 119;
    }

    @Override
    public int getSlotCount() {
        return 10;
    }

    @Override
    public Identifier getId() {
        return EivCommonPlugin.SEQUENCED_ASSEMBLY.getId();
    }

    @Override
    public ItemStack getIcon() {
        return AllItems.PRECISION_MECHANISM.getDefaultInstance();
    }
}
