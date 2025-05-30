package com.upo.createnetherindustry.compat.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.compat.jei.DoubleItemIcon;
import com.simibubi.create.compat.jei.EmptyBackground;
import com.simibubi.create.compat.jei.category.ProcessingViaFanCategory;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.upo.createnetherindustry.CreateNetherIndustry;
import com.upo.createnetherindustry.content.recipes.SoulStrippingRecipe;
import com.upo.createnetherindustry.registry.CNIRecipes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.createmod.catnip.gui.element.GuiGameElement;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import com.upo.createnetherindustry.registry.CNIBlocks;

import java.util.List;
import java.util.function.Supplier;

public class SoulStrippingCategory extends ProcessingViaFanCategory<SoulStrippingRecipe> {

    public static final RecipeType<SoulStrippingRecipe> TYPE =
            new RecipeType<>(
                    CreateNetherIndustry.asResource("soul_stripping"),
                    SoulStrippingRecipe.class
            );

    public SoulStrippingCategory(Info<SoulStrippingRecipe> info) {
        super(info);
    }

    public static SoulStrippingCategory create(IGuiHelper guiHelper) {
        Component title = Component.translatable("recipe.createnetherindustry.soul_stripping");
        IDrawable background = new EmptyBackground(178, 72);

        IDrawable icon = new DoubleItemIcon(
                () -> AllItems.PROPELLER.asStack(),
                () -> new ItemStack(CNIBlocks.SOUL_STRIPPING_MEDIUM.get())
        );

        Supplier<ItemStack> catalystStackSupplier = () -> {
            ItemStack stack = AllBlocks.ENCASED_FAN.asStack();
            stack.set(DataComponents.CUSTOM_NAME, Component.translatable("recipe.createnetherindustry.soul_stripping.fan").withStyle(style -> style.withItalic(false)));
            return stack;
        };

        Info<SoulStrippingRecipe> info = new Info<>(
                TYPE,
                title,
                background,
                icon,
                SoulStrippingCategory::getAllRecipes,
                List.of(catalystStackSupplier)
        );
        return new SoulStrippingCategory(info);
    }

    private static List<RecipeHolder<SoulStrippingRecipe>> getAllRecipes() {

        return CNIJEIPlugin.getRecipeManager().getAllRecipesFor(CNIRecipes.SOUL_STRIPPING_TYPE.get());
    }

    @Override
    public void draw(SoulStrippingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        renderWidgets(graphics, recipe, mouseX, mouseY);

        PoseStack matrixStack = graphics.pose();

        matrixStack.pushPose();
        translateFan(matrixStack);
        matrixStack.mulPose(Axis.XP.rotationDegrees(-12.5f));
        matrixStack.mulPose(Axis.YP.rotationDegrees(22.5f));

        AnimatedKinetics.defaultBlockElement(AllPartialModels.ENCASED_FAN_INNER)
                .rotateBlock(180, 0, AnimatedKinetics.getCurrentAngle() * 16)
                .scale(SCALE)
                .render(graphics);

        AnimatedKinetics.defaultBlockElement(AllBlocks.ENCASED_FAN.getDefaultState())
                .rotateBlock(0, 180, 0)
                .atLocal(0, 0, 0)
                .scale(SCALE)
                .render(graphics);

        GuiGameElement.of(CNIBlocks.SOUL_STRIPPING_MEDIUM.get().defaultBlockState())
                .scale(SCALE)
                .atLocal(0, 0, 2)
                .lighting(AnimatedKinetics.DEFAULT_LIGHTING)
                .render(graphics);

        matrixStack.popPose();
    }


    @Override
    @Deprecated
    protected void renderAttachedBlock(GuiGraphics graphics) {
    }
}

