package com.upo.createnetherindustry.registry;

import com.upo.createnetherindustry.content.recipes.condenser.CondensingRecipe;
import com.upo.createnetherindustry.content.recipes.soulstrip.SoulStrippingRecipeSerializer;
import com.upo.createnetherindustry.CreateNetherIndustry;
import com.upo.createnetherindustry.content.recipes.CNIRecipeTypeInfo;
import com.upo.createnetherindustry.content.recipes.soulstrip.SoulStrippingRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CNIRecipes {

    private static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, CreateNetherIndustry.MODID);

    private static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, CreateNetherIndustry.MODID);

    public static final CNIRecipeTypeInfo<SoulStrippingRecipe> SOUL_STRIPPING_TYPE_INFO =
            register("soul_stripping", SoulStrippingRecipeSerializer::new);

    public static final CNIRecipeTypeInfo<CondensingRecipe> CONDENSING_TYPE_INFO =
            register("soul_condensing", CondensingRecipe.Serializer::new);

    public static void register(IEventBus modBus) {
        TYPES.register(modBus);
        SERIALIZERS.register(modBus);
    }

    private static <R extends Recipe<?>> CNIRecipeTypeInfo<R> register(String name, Supplier<? extends RecipeSerializer<R>> serializerSupplier) {
        return new CNIRecipeTypeInfo<>(name, serializerSupplier, SERIALIZERS, TYPES);
    }
}



