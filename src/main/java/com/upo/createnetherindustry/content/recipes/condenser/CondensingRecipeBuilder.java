package com.upo.createnetherindustry.content.recipes.condenser;

import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.upo.createnetherindustry.CreateNetherIndustry;
import com.upo.createnetherindustry.data.recipe.CNICustomProcessingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.fluids.FluidStack;

public class CondensingRecipeBuilder extends CNICustomProcessingRecipeBuilder<
        CondensingRecipeParams,
        CondensingRecipe,
        CondensingRecipeBuilder
        > {

    public CondensingRecipeBuilder(ResourceLocation id) {
        super(CondensingRecipe::new, id);
    }

    @Override
    protected CondensingRecipeParams createParams(ResourceLocation id) {
        return new CondensingRecipeParams(id);
    }

    public static CondensingRecipeBuilder builder(String recipePathName) {
        return new CondensingRecipeBuilder(ResourceLocation.fromNamespaceAndPath(CreateNetherIndustry.MODID, recipePathName));
    }

    public CondensingRecipeBuilder inputFluid(FluidStack fluidStack) {
        return this.require(fluidStack.getFluid(), fluidStack.getAmount());
    }

    public CondensingRecipeBuilder inputFluid(FluidIngredient fluidIngredient) {
        return this.require(fluidIngredient);
    }

    public CondensingRecipeBuilder outputFluid(FluidStack fluidStack) {
        return this.output(fluidStack);
    }

}


