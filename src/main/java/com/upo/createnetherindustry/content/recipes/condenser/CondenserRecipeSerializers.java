package com.upo.createnetherindustry.content.recipes.condenser;

import com.upo.createnetherindustry.CreateNetherIndustry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CondenserRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, CreateNetherIndustry.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, CondenserRecipeSerializer> SOUL_CONDENSING_SERIALIZER =
            RECIPE_SERIALIZERS.register("soul_condensing", CondenserRecipeSerializer::new);

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
    }

}
