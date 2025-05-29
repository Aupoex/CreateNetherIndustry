package com.upo.createnetherindustry.content.recipes.condenser;

import com.mojang.serialization.MapCodec;
import com.upo.createnetherindustry.content.recipes.CNIRecipeParams;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public class CondensingRecipeParams extends CNIRecipeParams {
    public static final MapCodec<CondensingRecipeParams> CODEC = createCodec(CondensingRecipeParams::new);
    public static final StreamCodec<RegistryFriendlyByteBuf, CondensingRecipeParams> STREAM_CODEC = createStreamCodec(CondensingRecipeParams::new);

    public CondensingRecipeParams(ResourceLocation id) {
        super(id);
    }
}


