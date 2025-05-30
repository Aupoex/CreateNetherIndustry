package com.upo.createnetherindustry.compat.jei;

import com.google.common.base.Preconditions;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.upo.createnetherindustry.CreateNetherIndustry;
import com.upo.createnetherindustry.registry.CNIBlocks;
import com.upo.createnetherindustry.registry.CNIItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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

        ItemStack soulItemStack = new ItemStack(CNIItems.SOUL_ITEM.get());
        if (!soulItemStack.isEmpty()) {
            Component soul_description_alt1 = Component.translatable("jei.createnetherindustry.info.soul_item_alt.line1");
            Component soul_description_alt2 = Component.translatable("jei.createnetherindustry.info.soul_item_alt.line2");
            registration.addItemStackInfo(soulItemStack, soul_description_alt1, soul_description_alt2);
        }
        ItemStack mediumItemStack = new ItemStack(CNIBlocks.SOUL_STRIPPING_MEDIUM.get());
        if (!mediumItemStack.isEmpty()) {
            Component medium_description_alt1 = Component.translatable("jei.createnetherindustry.info.medium.line1");
            Component medium_description_alt2 = Component.translatable("jei.createnetherindustry.info.medium.line2");
            registration.addItemStackInfo(mediumItemStack, medium_description_alt1, medium_description_alt2);
        }
        ItemStack blazeTwigItemStack = new ItemStack(CNIItems.BLAZE_TWIG.get());
        if (!blazeTwigItemStack.isEmpty()) {
            Component twig_description_alt1 = Component.translatable("jei.createnetherindustry.info.blaze_twig.line1");
            Component twig_description_alt2 = Component.translatable("jei.createnetherindustry.info.blaze_twig.line2");
            registration.addItemStackInfo(blazeTwigItemStack, twig_description_alt1, twig_description_alt2);
        }
        ItemStack ancientMechanismItemStack = new ItemStack(CNIItems.ANCIENT_MECHANISM.get());
        if (!ancientMechanismItemStack.isEmpty()) {
            Component am_description_alt1 = Component.translatable("jei.createnetherindustry.info.ancient_mechanism.line1");
            Component am_description_alt2 = Component.translatable("jei.createnetherindustry.info.ancient_mechanism.line2");
            registration.addItemStackInfo(ancientMechanismItemStack, am_description_alt1, am_description_alt2);
        }
        ItemStack soulBlazePickaxeItemStack = new ItemStack(CNIItems.SOUL_BLAZE_PICKAXE.get());
        if (!soulBlazePickaxeItemStack.isEmpty()) {
            Component sbp_description_alt1 = Component.translatable("jei.createnetherindustry.info.soul_blaze_pickaxe.line1");
            Component sbp_description_alt2 = Component.translatable("jei.createnetherindustry.info.soul_blaze_pickaxe.line2");
            registration.addItemStackInfo(soulBlazePickaxeItemStack, sbp_description_alt1, sbp_description_alt2);
        }
        ItemStack soulBlazeAxeItemStack = new ItemStack(CNIItems.SOUL_BLAZE_AXE.get());
        if (!soulBlazeAxeItemStack.isEmpty()) {
            Component sba_description_alt1 = Component.translatable("jei.createnetherindustry.info.soul_blaze_axe.line1");
            Component sba_description_alt2 = Component.translatable("jei.createnetherindustry.info.soul_blaze_axe.line2");
            registration.addItemStackInfo(soulBlazeAxeItemStack, sba_description_alt1, sba_description_alt2);
        }
        ItemStack goldenCarrotItemStack = new ItemStack(Items.GOLDEN_CARROT);
        if (!goldenCarrotItemStack.isEmpty()) {
            Component golden_carrot_description_alt1 = Component.translatable("jei.createnetherindustry.info.golden_carrot.line1");
            Component golden_carrot_description_alt2 = Component.translatable("jei.createnetherindustry.info.golden_carrot.line2");
            registration.addItemStackInfo(goldenCarrotItemStack, golden_carrot_description_alt1, golden_carrot_description_alt2);
        }
        ItemStack witherRoseItemStack = new ItemStack(Items.WITHER_ROSE);
        if (!witherRoseItemStack.isEmpty()) {
            Component wither_rose_description_alt1 = Component.translatable("jei.createnetherindustry.info.wither_rose.line1");
            Component wither_rose_description_alt2 = Component.translatable("jei.createnetherindustry.info.wither_rose.line2");
            registration.addItemStackInfo(witherRoseItemStack, wither_rose_description_alt1, wither_rose_description_alt2);
        }
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
