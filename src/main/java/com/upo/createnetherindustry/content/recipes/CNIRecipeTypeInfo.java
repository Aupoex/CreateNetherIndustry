package com.upo.createnetherindustry.content.recipes;

import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;

public class CNIRecipeTypeInfo<T extends Recipe<?>> implements IRecipeTypeInfo {
    private final DeferredHolder<RecipeSerializer<?>, ? extends RecipeSerializer<? extends T>> serializer;
    private final DeferredHolder<RecipeType<?>, ? extends RecipeType<T>> type;

    public CNIRecipeTypeInfo(
            DeferredHolder<RecipeSerializer<?>, ? extends RecipeSerializer<? extends T>> serializer,
            DeferredHolder<RecipeType<?>, ? extends RecipeType<T>> type
    ) {
        this.serializer = serializer;
        this.type = type;
    }

    @Override
    public ResourceLocation getId() {
        return serializer.getId();
    }

    @Override
    @SuppressWarnings("unchecked")
    public RecipeSerializer<T> getSerializer() {
        return (RecipeSerializer<T>) serializer.get();
    }

    @Override
    @SuppressWarnings("unchecked")
    public RecipeType<T> getType() {
        return (RecipeType<T>) type.get();
    }

}

