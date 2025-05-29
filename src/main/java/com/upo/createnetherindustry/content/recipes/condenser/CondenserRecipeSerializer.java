package com.upo.createnetherindustry.content.recipes.condenser;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class CondenserRecipeSerializer implements RecipeSerializer<CondensingRecipe> {

    private final MapCodec<CondensingRecipe> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, CondensingRecipe> streamCodec;

    public CondenserRecipeSerializer() {
        this.codec = CondensingRecipeParams.CODEC.xmap(
                CondensingRecipe::new,
                CondensingRecipe::getCNIParams
        );

        this.streamCodec = CondensingRecipeParams.STREAM_CODEC.map(
                CondensingRecipe::new,
                CondensingRecipe::getCNIParams
        );
    }

    @Override
    public MapCodec<CondensingRecipe> codec() {
        return this.codec;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, CondensingRecipe> streamCodec() {
        return this.streamCodec;
    }
}




