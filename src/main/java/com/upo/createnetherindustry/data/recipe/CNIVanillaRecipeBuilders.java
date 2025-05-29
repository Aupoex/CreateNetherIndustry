package com.upo.createnetherindustry.data.recipe;

import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;


public class CNIVanillaRecipeBuilders {

    public static ShapedRecipeBuilder shaped(net.minecraft.world.level.ItemLike result, int count) {
        return ShapedRecipeBuilder.shaped(net.minecraft.data.recipes.RecipeCategory.BUILDING_BLOCKS, result, count);
    }

    public static ShapedRecipeBuilder shaped(net.minecraft.world.level.ItemLike result) {
        return shaped(result, 1);
    }

    public static ShapelessRecipeBuilder shapeless(net.minecraft.world.level.ItemLike result, int count) {
        return ShapelessRecipeBuilder.shapeless(net.minecraft.data.recipes.RecipeCategory.MISC, result, count);
    }

    public static ShapelessRecipeBuilder shapeless(net.minecraft.world.level.ItemLike result) {
        return shapeless(result, 1);
    }
}
