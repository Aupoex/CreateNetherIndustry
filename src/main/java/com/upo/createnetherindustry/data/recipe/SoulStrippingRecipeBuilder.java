package com.upo.createnetherindustry.data.recipe;

import com.upo.createnetherindustry.CreateNetherIndustry;
import com.upo.createnetherindustry.content.recipes.SoulStrippingRecipeParams;
import com.upo.createnetherindustry.content.recipes.SoulStrippingRecipe;
import net.minecraft.resources.ResourceLocation;

public class SoulStrippingRecipeBuilder extends CNICustomProcessingRecipeBuilder<
        SoulStrippingRecipeParams,
        SoulStrippingRecipe,
        SoulStrippingRecipeBuilder
        > {

    public SoulStrippingRecipeBuilder(ResourceLocation id) {
        super(SoulStrippingRecipe::new, id);
    }

    @Override
    protected SoulStrippingRecipeParams createParams(ResourceLocation id) {

        return new SoulStrippingRecipeParams(id);
    }

    public static SoulStrippingRecipeBuilder builder(String name) {
        return new SoulStrippingRecipeBuilder(ResourceLocation.fromNamespaceAndPath(CreateNetherIndustry.MODID, name));
    }

    public static SoulStrippingRecipeBuilder builder(ResourceLocation id) {
        return new SoulStrippingRecipeBuilder(id);
    }
}


