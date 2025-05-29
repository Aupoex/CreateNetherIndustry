package com.upo.createnetherindustry.data.recipe;

import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.upo.createnetherindustry.content.recipes.CNIRecipe;
import com.upo.createnetherindustry.content.recipes.CNIRecipeParams;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.conditions.NotCondition;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class CNICustomProcessingRecipeBuilder<
        P extends CNIRecipeParams,
        R extends CNIRecipe<?, P>,
        T_BUILDER extends CNICustomProcessingRecipeBuilder<P, R, T_BUILDER>
        > extends ProcessingRecipeBuilder<R> {

    protected final Function<P, R> recipeFactory;
    protected final P typedParams;

    protected final List<ICondition> recipeConditions = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public CNICustomProcessingRecipeBuilder(Function<P, R> recipeFactory, ResourceLocation id) {
        super(null, id);
        this.recipeFactory = recipeFactory;

        P createdParams = createParams(id);
        this.params = createdParams;
        this.typedParams = createdParams;
    }

    protected abstract P createParams(ResourceLocation id);

    protected P getTypedParams() {
        return (P) this.params;
    }

    @Override
    public R build() {
        return recipeFactory.apply(getTypedParams());
    }

    @SuppressWarnings("unchecked")
    protected T_BUILDER self() {
        return (T_BUILDER) this;
    }


    @Override
    public T_BUILDER require(TagKey<Item> tag) {
        super.require(tag);
        return self();
    }

    @Override
    public T_BUILDER require(ItemLike item) {
        super.require(item);
        return self();
    }

    @Override
    public T_BUILDER require(Ingredient ingredient) {
        super.require(ingredient);
        return self();
    }

    @Override
    public T_BUILDER require(Fluid fluid, int amount) {
        super.require(fluid, amount);
        return self();
    }

    @Override
    public T_BUILDER require(TagKey<Fluid> fluidTag, int amount) {
        super.require(fluidTag, amount);
        return self();
    }

    @Override
    public T_BUILDER require(FluidIngredient ingredient) {
        super.require(ingredient);
        return self();
    }

    @Override
    public T_BUILDER output(ItemLike item) {
        super.output(item);
        return self();
    }

    @Override
    public T_BUILDER output(float chance, ItemLike item) {
        super.output(chance, item);
        return self();
    }

    @Override
    public T_BUILDER output(ItemLike item, int amount) {
        super.output(item, amount);
        return self();
    }

    @Override
    public T_BUILDER output(float chance, ItemLike item, int amount) {
        super.output(chance, item, amount);
        return self();
    }

    @Override
    public T_BUILDER output(ItemStack output) {
        super.output(output);
        return self();
    }

    @Override
    public T_BUILDER output(float chance, ItemStack output) {
        super.output(chance, output);
        return self();
    }

    @Override
    public T_BUILDER output(ProcessingOutput output) {
        super.output(output);
        return self();
    }

    @Override
    public T_BUILDER output(Fluid fluid, int amount) {
        super.output(new FluidStack(fluid, amount));
        return self();
    }

    @Override
    public T_BUILDER output(FluidStack fluidStack) {
        super.output(fluidStack);
        return self();
    }

    @Override
    public T_BUILDER duration(int ticks) {
        super.duration(ticks);
        return self();
    }

    @Override
    public T_BUILDER averageProcessingDuration() {
        super.averageProcessingDuration();
        return self();
    }

    @Override
    public T_BUILDER requiresHeat(HeatCondition condition) {
        super.requiresHeat(condition);
        return self();
    }

    @Override
    public T_BUILDER toolNotConsumed() {
        super.toolNotConsumed();
        return self();
    }

    @Override
    public T_BUILDER withCondition(ICondition condition) {
        this.recipeConditions.add(condition);
        return self();
    }

    @Override
    public T_BUILDER whenModLoaded(String modid) {
        return withCondition(new ModLoadedCondition(modid));
    }

    @Override
    public T_BUILDER whenModMissing(String modid) {
        return withCondition(new NotCondition(new ModLoadedCondition(modid)));
    }

    public void build(RecipeOutput consumer, ResourceLocation idForOutput) {
        R recipe = build();
        consumer.accept(idForOutput, recipe, null, recipeConditions.toArray(new ICondition[0]));
    }

    public void build(RecipeOutput consumer) {

        R recipe = build();
        IRecipeTypeInfo recipeTypeInfo = recipe.getTypeInfo();
        if (recipeTypeInfo == null) {
            throw new IllegalStateException("Recipe: " + this.recipeId + " does not have a RecipeTypeInfo.");
        }
        ResourceLocation typeSpecificPath = recipeTypeInfo.getId();
        ResourceLocation finalId = ResourceLocation.fromNamespaceAndPath(
                this.recipeId.getNamespace(),
                typeSpecificPath.getPath() + "/" + this.recipeId.getPath()
        );
        build(consumer, finalId);
    }

    public void save(RecipeOutput consumer) {
        R recipe = build();
        IRecipeTypeInfo recipeTypeInfo = recipe.getTypeInfo();
        if (recipeTypeInfo == null) {
            throw new IllegalStateException("Recipe: " + this.recipeId + " does not have a RecipeTypeInfo. This is required for default ID construction.");
        }
        ResourceLocation typePathId = recipeTypeInfo.getId();

        ResourceLocation finalId = ResourceLocation.fromNamespaceAndPath(
                this.recipeId.getNamespace(),
                typePathId.getPath() + "/" + this.recipeId.getPath()
        );
        this.save(consumer, finalId);
    }

    public void save(RecipeOutput consumer, ResourceLocation idForOutput) {
        R recipe = build();
        consumer.accept(idForOutput, recipe, null, recipeConditions.toArray(new ICondition[0]));
    }

}

