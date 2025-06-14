package com.upo.createnetherindustry.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.upo.createnetherindustry.content.blockentities.SoulCondenserBlockEntity;
import net.createmod.catnip.animation.LerpedFloat;
import net.createmod.catnip.platform.NeoForgeCatnipServices;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;

public class SoulCondenserRenderer extends KineticBlockEntityRenderer<SoulCondenserBlockEntity> {

    public SoulCondenserRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(SoulCondenserBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
                              int light, int overlay) {
        renderRotatingCogBER(be, partialTicks, ms, buffer, light, overlay);
        renderInputFluid(be, partialTicks, ms, buffer, light, overlay);
    }

    protected void renderRotatingCogBER(SoulCondenserBlockEntity be, float partialTicks, PoseStack ms,
                                        MultiBufferSource buffer, int light, int overlay) {

        BlockState blockState = be.getBlockState();
        SuperByteBuffer cogWheelSbb = CachedBuffers.partial(AllPartialModels.SHAFTLESS_COGWHEEL, blockState);

        Direction.Axis axis = Direction.Axis.Y;
        float angleRadians = KineticBlockEntityRenderer.getAngleForBe(be, be.getBlockPos(), axis);

        KineticBlockEntityRenderer.kineticRotationTransform(cogWheelSbb, be, axis, angleRadians, light);

        ms.pushPose();

        ms.translate(0, 0, 0);

        cogWheelSbb.overlay(overlay).renderInto(ms, buffer.getBuffer(RenderType.cutoutMipped()));

        ms.popPose();
    }


    protected void renderInputFluid(SoulCondenserBlockEntity be, float partialTicks, PoseStack ms,
                                    MultiBufferSource buffer, int light, int overlay) {
        SmartFluidTankBehaviour inputTankBev = be.getInputTankBehaviour();
        if (inputTankBev == null) {
            return;
        }

        SmartFluidTankBehaviour.TankSegment tankSegment = inputTankBev.getPrimaryTank();
        FluidStack fluidStack = tankSegment.getRenderedFluid();
        if (fluidStack.isEmpty()) {
            return;
        }

        LerpedFloat fluidLerpedLevel = tankSegment.getFluidLevel();
        if (fluidLerpedLevel == null) return;
        float fluidLevelRatio = fluidLerpedLevel.getValue(partialTicks);

        if (fluidLevelRatio < 1e-5f) {
            return;
        }

        final float xMin_coord = 1f / 16f;
        final float xMax_coord = 14f / 16f;
        final float zMin_coord = 1f / 16f;
        final float zMax_coord = 14f / 16f;

        final float renderAreaMinY_coord = 10f / 16f;
        final float renderAreaMaxY_coord = 14f / 16f;
        final float renderAreaHeight_coord = renderAreaMaxY_coord - renderAreaMinY_coord;

        float displayFluidHeight = Mth.clamp(fluidLevelRatio * renderAreaHeight_coord, 0, renderAreaHeight_coord);
        if (fluidStack.getAmount() > 0 && displayFluidHeight < (0.5f / 16f) && renderAreaHeight_coord > 0) {
            displayFluidHeight = (0.5f / 16f);
        }

        if (displayFluidHeight <= 1e-5f) return;

        float actualYMin = renderAreaMinY_coord;
        float actualYMax = renderAreaMinY_coord + displayFluidHeight;

        ms.pushPose();

        NeoForgeCatnipServices.FLUID_RENDERER.renderFluidBox(
                fluidStack,
                xMin_coord, actualYMin, zMin_coord,
                xMax_coord, actualYMax, zMax_coord,
                buffer,
                ms,
                light,
                true,true
        );

        ms.popPose();
    }

}
