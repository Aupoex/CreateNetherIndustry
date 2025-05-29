package com.upo.createnetherindustry.content.recipes;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public class SoulStrippingRecipeParams extends CNIRecipeParams {

    public static final MapCodec<SoulStrippingRecipeParams> CODEC = createCodec(SoulStrippingRecipeParams::new);

    public static final StreamCodec<RegistryFriendlyByteBuf, SoulStrippingRecipeParams> STREAM_CODEC = createStreamCodec(SoulStrippingRecipeParams::new);

    public SoulStrippingRecipeParams(ResourceLocation id) {
        super(id);
    }
}


