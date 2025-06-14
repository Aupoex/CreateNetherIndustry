package com.upo.createnetherindustry.content.recipes.condenser;

import com.simibubi.create.content.processing.recipe.ProcessingRecipeParams;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class CondensingRecipeParams extends ProcessingRecipeParams {

    public CondensingRecipeParams() {
        super();
    }

    public static final MapCodec<CondensingRecipeParams> CODEC =
            ProcessingRecipeParams.codec(CondensingRecipeParams::new);

    public static final StreamCodec<RegistryFriendlyByteBuf, CondensingRecipeParams> STREAM_CODEC =
            ProcessingRecipeParams.streamCodec(CondensingRecipeParams::new);

}


