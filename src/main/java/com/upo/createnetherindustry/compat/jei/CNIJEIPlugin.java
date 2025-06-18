package com.upo.createnetherindustry.compat.jei;

import com.google.common.base.Preconditions;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.upo.createnetherindustry.CreateNetherIndustry;
import com.upo.createnetherindustry.registry.CNIBlocks;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLLoader;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class CNIJEIPlugin implements IModPlugin{
    public static final ResourceLocation ID = CreateNetherIndustry.asResource("jei_plugin");
    private final List<IRecipeCategory<?>> categories = new ArrayList<>();


    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        List<IRecipeCategory<?>> localCategories = new ArrayList<>();

        localCategories.add(SoulStrippingCategory.create(guiHelper));
        localCategories.add(SoulCondensingCategory.create(guiHelper));

        registration.addRecipeCategories(localCategories.toArray(new IRecipeCategory[0]));

        this.categories.clear();
        this.categories.addAll(localCategories);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {

        for (IRecipeCategory<?> category : categories) {
            if (category instanceof CreateRecipeCategory) {
                ((CreateRecipeCategory<?>) category).registerRecipes(registration);
            }
        }
        JEIInfo.registerInfoPages(registration);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(AllBlocks.ENCASED_FAN.asStack(), SoulStrippingCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(CNIBlocks.SOUL_STRIPPING_MEDIUM.get()), SoulStrippingCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(CNIBlocks.SOUL_CONDENSER.get()), SoulCondensingCategory.TYPE);
    }

    public static Level getLevel() {
        if (FMLLoader.getDist() != Dist.CLIENT)
            throw new IllegalStateException("Retreiving client level is only supported for client");
        var minecraft = Minecraft.getInstance();
        Preconditions.checkNotNull(minecraft, "Minecraft instance was null");
        var level = minecraft.level;
        Preconditions.checkNotNull(level, "Client level was null");
        return level;
    }

    public static RecipeManager getRecipeManager() {
        if (FMLLoader.getDist() != Dist.CLIENT)
            throw new IllegalStateException("Retreiving recipe manager from client level is only supported for client");
        var minecraft = Minecraft.getInstance();
        Preconditions.checkNotNull(minecraft, "Minecraft instance was null");
        var level = minecraft.level;
        Preconditions.checkNotNull(level, "Client level was null");
        return level.getRecipeManager();
    }
}
