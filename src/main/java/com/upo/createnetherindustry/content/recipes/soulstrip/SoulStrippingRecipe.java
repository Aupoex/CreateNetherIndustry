package com.upo.createnetherindustry.content.recipes.soulstrip;

import com.simibubi.create.content.processing.recipe.ProcessingRecipeParams;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import com.upo.createnetherindustry.registry.CNIRecipes;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

public class SoulStrippingRecipe extends StandardProcessingRecipe<SingleRecipeInput> {

    public SoulStrippingRecipe(ProcessingRecipeParams params) {
        super(CNIRecipes.SOUL_STRIPPING_TYPE_INFO, params);
    }

    @Override
    public boolean matches(SingleRecipeInput inv, Level worldIn) {
        if (inv.isEmpty()) {
            return false;
        }
        if (this.getIngredients().isEmpty()) {
            return false;
        }
        return this.getIngredients().get(0).test(inv.getItem(0));
    }

    @Override
    protected int getMaxInputCount() {
        return 1;
    }

    @Override
    protected int getMaxOutputCount() {
        return 2;
    }
}
