package com.upo.createnetherindustry.content.recipes.condenser;

import com.upo.createnetherindustry.content.recipes.CNIRecipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.fluids.FluidStack;

public class CondensingRecipe extends CNIRecipe<RecipeInput, CondensingRecipeParams> implements ICondensingRecipe {

    public CondensingRecipe(CondensingRecipeParams params) {
        super(CondenserRecipeType.SOUL_CONDENSING_TYPE_INFO, params);
    }

    @Override
    public FluidStack getInputFluid() {
        return this.fluidIngredients.get(0).getMatchingFluidStacks().get(0).copy();
    }

    @Override
    public FluidStack getOutputFluid() {
        return this.fluidResults.get(0).copy();
    }

    @Override
    public int getProcessingDuration() {
        return this.processingDuration;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CondenserRecipeSerializers.SOUL_CONDENSING_SERIALIZER.get();
    }

    @Override protected int getMaxInputCount() { return 0; }
    @Override protected int getMaxOutputCount() { return 0; }
    @Override protected int getMaxFluidInputCount() { return 1; }
    @Override protected int getMaxFluidOutputCount() { return 1; }
    @Override protected boolean canSpecifyDuration() { return true; }
    @Override protected boolean canRequireHeat() { return false; }
}

