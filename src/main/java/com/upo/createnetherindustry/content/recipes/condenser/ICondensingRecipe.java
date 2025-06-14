package com.upo.createnetherindustry.content.recipes.condenser;

import net.neoforged.neoforge.fluids.FluidStack;

public interface ICondensingRecipe {

    FluidStack getInputFluid();

    FluidStack getOutputFluid();

    int getProcessingDuration();

}

