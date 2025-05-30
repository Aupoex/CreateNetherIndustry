package com.upo.createnetherindustry.registry;

import com.upo.createnetherindustry.CreateNetherIndustry;
import com.upo.createnetherindustry.content.recipes.CNIRecipeTypeInfo;
import com.upo.createnetherindustry.content.recipes.SoulStrippingRecipe;
import com.upo.createnetherindustry.content.recipes.SoulStrippingRecipeSerializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CNIRecipes {

    private static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, CreateNetherIndustry.MODID);

    private static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, CreateNetherIndustry.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, SoulStrippingRecipeSerializer> SOUL_STRIPPING_SERIALIZER =
            SERIALIZERS.register("soul_stripping", SoulStrippingRecipeSerializer::new);

    public static final DeferredHolder<RecipeType<?>, RecipeType<SoulStrippingRecipe>> SOUL_STRIPPING_TYPE =
            TYPES.register("soul_stripping", () -> RecipeType.simple(SOUL_STRIPPING_SERIALIZER.getId()));

    public static final CNIRecipeTypeInfo<SoulStrippingRecipe> SOUL_STRIPPING_TYPE_INFO =
            new CNIRecipeTypeInfo<>(SOUL_STRIPPING_SERIALIZER, SOUL_STRIPPING_TYPE);


    public static void register(IEventBus modBus) {
        TYPES.register(modBus);
        SERIALIZERS.register(modBus);
    }

}

