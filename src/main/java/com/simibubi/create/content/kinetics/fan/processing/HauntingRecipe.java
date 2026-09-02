package com.simibubi.create.content.kinetics.fan.processing;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.simibubi.create.AllRecipeSerializers;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.recipe.CreateSingleStackRollableRecipe;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.List;

public record HauntingRecipe(List<ProcessingOutput> results,
                             Ingredient ingredient) implements CreateSingleStackRollableRecipe {
    public static final MapCodec<HauntingRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        ProcessingOutput.CODEC.listOf(1, 12).fieldOf("results").forGetter(HauntingRecipe::results),
        Ingredient.CODEC.fieldOf("ingredient").forGetter(HauntingRecipe::ingredient)
    ).apply(instance, HauntingRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, HauntingRecipe> STREAM_CODEC = StreamCodec.composite(
        ProcessingOutput.STREAM_CODEC.apply(ByteBufCodecs.list()),
        HauntingRecipe::results,
        Ingredient.CONTENTS_STREAM_CODEC,
        HauntingRecipe::ingredient,
        HauntingRecipe::new
    );
    public static final RecipeSerializer<HauntingRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    @Override
    public RecipeSerializer<HauntingRecipe> getSerializer() {
        return AllRecipeSerializers.HAUNTING;
    }

    @Override
    public RecipeType<HauntingRecipe> getType() {
        return AllRecipeTypes.HAUNTING;
    }
}
