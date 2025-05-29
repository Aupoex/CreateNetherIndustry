package com.upo.createnetherindustry.content.recipes.condenser;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;

public interface ICondensingRecipe extends Recipe<RecipeInput> {

    FluidStack getInputFluid();
    FluidStack getOutputFluid();
    int getProcessingDuration();

    @Override default boolean matches(RecipeInput pInput, Level pLevel) { return true; }
    @Override default ItemStack assemble(RecipeInput pInput, HolderLookup.Provider pRegistries) { return ItemStack.EMPTY; }
    @Override default boolean canCraftInDimensions(int pWidth, int pHeight) { return false; }
    @Override default ItemStack getResultItem(HolderLookup.Provider pRegistries) { return ItemStack.EMPTY; }
    @Override default NonNullList<Ingredient> getIngredients() { return NonNullList.create(); }
    @Override default boolean isSpecial() { return true; }
}



