package com.upo.createnetherindustry.content.recipes;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.world.item.crafting.RecipeInput;

public abstract class CNIRecipe<I extends RecipeInput, P extends CNIRecipeParams> extends ProcessingRecipe<I> {

    protected final P cniParams;
    public CNIRecipe(IRecipeTypeInfo typeInfo, P params) {
        super(typeInfo, params);
        this.cniParams = params;
    }
    public P getCNIParams() {
        return cniParams;
    }
}

