package com.upo.createnetherindustry.content.recipes;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class SoulStrippingRecipeSerializer implements RecipeSerializer<SoulStrippingRecipe> {

    private final MapCodec<SoulStrippingRecipe> codec;

    private final StreamCodec<RegistryFriendlyByteBuf, SoulStrippingRecipe> streamCodec;

    public SoulStrippingRecipeSerializer() {
        this.codec = SoulStrippingRecipeParams.CODEC.xmap(
                SoulStrippingRecipe::new,
                SoulStrippingRecipe::getCNIParams
        );

        this.streamCodec = SoulStrippingRecipeParams.STREAM_CODEC.map(
                SoulStrippingRecipe::new,
                SoulStrippingRecipe::getCNIParams
        );
    }

    @Override
    public MapCodec<SoulStrippingRecipe> codec() {
        return this.codec;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, SoulStrippingRecipe> streamCodec() {
        return this.streamCodec;
    }
}


