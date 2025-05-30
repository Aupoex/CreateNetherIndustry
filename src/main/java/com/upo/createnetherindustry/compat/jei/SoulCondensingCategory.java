package com.upo.createnetherindustry.compat.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.compat.jei.EmptyBackground;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.upo.createnetherindustry.CreateNetherIndustry;
import com.upo.createnetherindustry.content.recipes.condenser.CondenserRecipeType;
import com.upo.createnetherindustry.content.recipes.condenser.CondensingRecipe;
import com.upo.createnetherindustry.registry.CNIBlocks;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.fluids.FluidStack;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class SoulCondensingCategory extends CreateRecipeCategory<CondensingRecipe> {

    public static final RecipeType<CondensingRecipe> TYPE =
            new RecipeType<>(CreateNetherIndustry.asResource("soul_condensing"), CondensingRecipe.class);

    public SoulCondensingCategory(Info<CondensingRecipe> info) {
        super(info);
    }

    public static SoulCondensingCategory create(IGuiHelper guiHelper) {
        Supplier<List<RecipeHolder<CondensingRecipe>>> recipesSupplier = () ->
                CNIJEIPlugin.getRecipeManager()
                        .getAllRecipesFor(CondenserRecipeType.SOUL_CONDENSING_RECIPE_TYPE_DEFERRED.get())
                        .stream()
                        .filter(holder -> holder.value() instanceof CondensingRecipe)
                        .map(holder -> new RecipeHolder<>(holder.id(), (CondensingRecipe) holder.value()))
                        .collect(Collectors.toList());

        Supplier<ItemStack> condenserCatalystSupplier = () -> new ItemStack(CNIBlocks.SOUL_CONDENSER.get());

        List<Supplier<? extends ItemStack>> catalysts = List.of(condenserCatalystSupplier);

        Info<CondensingRecipe> info = new Info<>(
                TYPE,
                Component.translatable("recipe.createnetherindustry.soul_condensing"),
                new EmptyBackground(177, 70),
                guiHelper.createDrawableItemStack(new ItemStack(CNIBlocks.SOUL_CONDENSER.get())),
                recipesSupplier,
                catalysts
        );
        return new SoulCondensingCategory(info);
    }

    private static final int BG_WIDTH = 177;
    private static final int BG_HEIGHT = 80;
    private static final int FLUID_TANK_WIDTH = 16;
    private static final int FLUID_TANK_HEIGHT = 48;

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CondensingRecipe recipe, IFocusGroup focuses) {
        FluidStack inputFluid = recipe.getInputFluid();
        FluidStack outputFluid = recipe.getOutputFluid();

        int fluidVisualCapacity = 200;

        int inputTankX = 15 + 1;
        int inputTankY = (BG_HEIGHT - FLUID_TANK_HEIGHT) / 2 + 1;

        // 输出流体槽位置
        int outputTankX = BG_WIDTH - FLUID_TANK_WIDTH - 15 + 1;
        int outputTankY = inputTankY;

        builder.addSlot(RecipeIngredientRole.INPUT, inputTankX, inputTankY)
                .addFluidStack(inputFluid.getFluid(), inputFluid.getAmount())
                .setFluidRenderer(Math.max(fluidVisualCapacity, inputFluid.getAmount()), false, FLUID_TANK_WIDTH, FLUID_TANK_HEIGHT);

        builder.addSlot(RecipeIngredientRole.OUTPUT, outputTankX, outputTankY)
                .addFluidStack(outputFluid.getFluid(), outputFluid.getAmount())
                .setFluidRenderer(Math.max(fluidVisualCapacity, outputFluid.getAmount()), false, FLUID_TANK_WIDTH, FLUID_TANK_HEIGHT);
    }

    @Override
    public void draw(CondensingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {

        int inputTankBgX = 15;
        int inputTankBgY = (BG_HEIGHT - FLUID_TANK_HEIGHT) / 2;
        drawFluidTankBackground(graphics, inputTankBgX, inputTankBgY);

        int outputTankBgX = BG_WIDTH - FLUID_TANK_WIDTH - 15;
        int outputTankBgY = inputTankBgY;
        drawFluidTankBackground(graphics, outputTankBgX, outputTankBgY);

        int arrowX = (BG_WIDTH - AllGuiTextures.JEI_ARROW.getWidth()) / 2;
        int arrowY = (BG_HEIGHT - AllGuiTextures.JEI_ARROW.getHeight()) / 2 + 12;
        AllGuiTextures.JEI_ARROW.render(graphics, arrowX, arrowY);

        ItemStack soulCondenserStack = new ItemStack(CNIBlocks.SOUL_CONDENSER.get());
        PoseStack poseStack = graphics.pose();

        poseStack.pushPose();

        int itemCenterX = BG_WIDTH / 2;
        int itemCenterY = 30;
        float desiredVisualSize = 32.0f;

        poseStack.translate(itemCenterX, itemCenterY, 100.0F);

        poseStack.scale(desiredVisualSize, -desiredVisualSize, desiredVisualSize);

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        BakedModel bakedModel = itemRenderer.getModel(soulCondenserStack, null, null, 0);
        MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();

        itemRenderer.render(
                soulCondenserStack,
                ItemDisplayContext.GUI,
                false,
                poseStack,
                buffer,
                LightTexture.FULL_BRIGHT,
                OverlayTexture.NO_OVERLAY,
                bakedModel
        );

        buffer.endBatch();
        poseStack.popPose();

    }


    protected void drawFluidTankBackground(GuiGraphics graphics, int x, int y) {

        int borderWidth = FLUID_TANK_WIDTH + 2;
        int borderHeight = FLUID_TANK_HEIGHT + 2;
        graphics.fill(x, y, x + borderWidth, y + borderHeight, 0xFF373737);
        graphics.fill(x + 1, y + 1, x + borderWidth - 1, y + borderHeight - 1, 0xFF8b8b8b);
    }
}



