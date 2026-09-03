package com.simibubi.create.content.logistics.item.filter.attribute;

import com.mojang.serialization.Codec;
import com.simibubi.create.api.registry.CreateRegistries;
import com.simibubi.create.api.registry.CreateStreamCodecs;
import com.simibubi.create.catnip.codecs.CatnipCodecUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public interface ItemAttribute {
    Codec<ItemAttribute> CODEC = CreateRegistries.ITEM_ATTRIBUTE_TYPE.byNameCodec()
        .dispatch(ItemAttribute::getType, ItemAttributeType::codec);
    // Create extension registries are not NeoForge ID-synced registries on 26.2.
    // Use stable names so addon-provided attribute types are safe on the wire too.
    StreamCodec<RegistryFriendlyByteBuf, ItemAttributeType> TYPE_PACKET_CODEC =
        CreateStreamCodecs.registryByName(CreateRegistries.ITEM_ATTRIBUTE_TYPE);
    StreamCodec<RegistryFriendlyByteBuf, ItemAttribute> PACKET_CODEC = TYPE_PACKET_CODEC
        .dispatch(ItemAttribute::getType, ItemAttributeType::packetCodec);

    static CompoundTag saveStatic(ItemAttribute attribute, HolderLookup.Provider registries) {
        CompoundTag nbt = new CompoundTag();
        nbt.put("attribute", CatnipCodecUtils.encode(CODEC, registries, attribute).orElseThrow());
        return nbt;
    }

    @Nullable
    static ItemAttribute loadStatic(CompoundTag nbt, HolderLookup.Provider registries) {
        return CatnipCodecUtils.decodeOrNull(CODEC, registries, nbt.get("attribute"));
    }

    static List<ItemAttribute> getAllAttributes(ItemStack stack, Level level) {
        List<ItemAttribute> attributes = new ArrayList<>();
        for (ItemAttributeType type : CreateRegistries.ITEM_ATTRIBUTE_TYPE) {
            attributes.addAll(type.getAllAttributes(stack, level));
        }
        return attributes;
    }

    boolean appliesTo(ItemStack stack, Level world);

    ItemAttributeType getType();

    default MutableComponent format(boolean inverted) {
        return Component.translatable(
            "create.item_attributes." + getTranslationKey() + (inverted ? ".inverted" : ""),
            getTranslationParameters()
        );
    }

    String getTranslationKey();

    default Object[] getTranslationParameters() {
        return new String[0];
    }
}