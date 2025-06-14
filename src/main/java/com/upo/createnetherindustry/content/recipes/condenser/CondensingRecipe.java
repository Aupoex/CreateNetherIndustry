package com.upo.createnetherindustry.content.recipes.condenser;


import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import com.mojang.serialization.MapCodec;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import com.upo.createnetherindustry.registry.CNIRecipes;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;


public class CondensingRecipe extends ProcessingRecipe<RecipeInput, CondensingRecipeParams> implements ICondensingRecipe {

    public CondensingRecipe(CondensingRecipeParams params) {
        super(CNIRecipes.CONDENSING_TYPE_INFO, params);
    }

    public static Builder builder(ResourceLocation id) {
        return new Builder(id);
    }

    @Override
    public FluidStack getInputFluid() {
        if (this.fluidIngredients.isEmpty())
            return FluidStack.EMPTY;
        return this.fluidIngredients.get(0).getMatchingFluidStacks().get(0).copy();
    }

    @Override
    public FluidStack getOutputFluid() {
        if (this.fluidResults.isEmpty())
            return FluidStack.EMPTY;
        return this.fluidResults.get(0).copy();
    }

    @Override
    protected int getMaxInputCount() {
        return 4000;
    }

    @Override
    protected int getMaxOutputCount() {
        return 4000;
    }

    @Override
    protected int getMaxFluidInputCount() {
        return 1;
    }

    @Override
    protected int getMaxFluidOutputCount() {
        return 1;
    }

    @Override
    public int getProcessingDuration() {
        return this.processingDuration;
    }

    @Override
    public boolean matches(RecipeInput recipeInput, Level level) {
        return false;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CNIRecipes.CONDENSING_TYPE_INFO.getSerializer();
    }

    public static class Builder extends ProcessingRecipeBuilder<CondensingRecipeParams, CondensingRecipe, Builder> {

        public Builder(ResourceLocation recipeId) {
            super(CondensingRecipe::new, recipeId);
        }

        @Override
        protected CondensingRecipeParams createParams() {
            return new CondensingRecipeParams();
        }

        @Override
        public Builder self() {
            return this;
        }
    }

    public static class Serializer implements RecipeSerializer<CondensingRecipe> {
        private final MapCodec<CondensingRecipe> codec;
        private final StreamCodec<RegistryFriendlyByteBuf, CondensingRecipe> streamCodec;

        public Serializer() {
            this.codec = ProcessingRecipe.codec(CondensingRecipe::new, CondensingRecipeParams.CODEC);
            this.streamCodec = ProcessingRecipe.streamCodec(CondensingRecipe::new, CondensingRecipeParams.STREAM_CODEC);
        }

        @Override
        public MapCodec<CondensingRecipe> codec() { return this.codec; }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CondensingRecipe> streamCodec() { return this.streamCodec; }
    }
}

