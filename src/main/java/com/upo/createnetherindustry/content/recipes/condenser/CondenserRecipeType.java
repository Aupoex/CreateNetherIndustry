package com.upo.createnetherindustry.content.recipes.condenser;

import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.upo.createnetherindustry.CreateNetherIndustry;
import com.upo.createnetherindustry.content.recipes.CNIRecipeTypeInfo;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CondenserRecipeType {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES_REGISTRY =
            DeferredRegister.create(Registries.RECIPE_TYPE, CreateNetherIndustry.MODID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<ICondensingRecipe>> SOUL_CONDENSING_RECIPE_TYPE_DEFERRED =
            RECIPE_TYPES_REGISTRY.register("soul_condensing", () -> new RecipeType<ICondensingRecipe>() {
                @Override
                public String toString() {
                    return CreateNetherIndustry.MODID + ":soul_condensing";
                }
            });

    public static final IRecipeTypeInfo SOUL_CONDENSING_TYPE_INFO = new CNIRecipeTypeInfo<>(
            CondenserRecipeSerializers.SOUL_CONDENSING_SERIALIZER,
            SOUL_CONDENSING_RECIPE_TYPE_DEFERRED
    );

    public static void register(IEventBus eventBus) {
        RECIPE_TYPES_REGISTRY.register(eventBus);
    }
}

