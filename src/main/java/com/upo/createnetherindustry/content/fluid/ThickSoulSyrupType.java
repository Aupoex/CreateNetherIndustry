package com.upo.createnetherindustry.content.fluid;

import com.mojang.blaze3d.shaders.FogShape;
import com.simibubi.create.AllFluids;
import com.tterrag.registrate.builders.FluidBuilder;
import com.upo.createnetherindustry.CreateNetherIndustry;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.function.Consumer;

public class ThickSoulSyrupType extends AllFluids.TintedFluidType{

    private final ResourceLocation originalStillTextureRL;
    private final ResourceLocation originalFlowingTextureRL;

    private ThickSoulSyrupType(Properties properties, ResourceLocation stillTexture, ResourceLocation flowingTexture) {
        super(properties, stillTexture, flowingTexture);
        this.originalStillTextureRL = stillTexture;
        this.originalFlowingTextureRL = flowingTexture;
    }

    public static FluidBuilder.FluidTypeFactory create() {
        return (properties, stillTexture, flowingTexture) -> {
            return new ThickSoulSyrupType(properties, stillTexture, flowingTexture);
        };
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {

                ResourceLocation stillRL = ThickSoulSyrupType.this.originalStillTextureRL;

                return stillRL;
            }

            @Override
            public ResourceLocation getFlowingTexture() {

                ResourceLocation newFlowingRL = CreateNetherIndustry.asResource("fluid/thick_soul_syrup_flowing");
                return newFlowingRL;
            }



            @Override
            public int getTintColor(FluidStack stack) {
                return ThickSoulSyrupType.this.getTintColor(stack);
            }

            @Override
            public int getTintColor(FluidState state, BlockAndTintGetter getter, BlockPos pos) {
                return ThickSoulSyrupType.this.getTintColor(state, getter, pos);
            }

            @Override
            public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level,
                                                    int renderDistance, float darkenWorldAmount, @NotNull Vector3f fluidFogColor) {
                Vector3f customFogColor = ThickSoulSyrupType.this.getCustomFogColor();

                return customFogColor == null ? fluidFogColor : customFogColor;
            }

            @Override
            public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick,
                                        float nearDistance, float farDistance, @NotNull FogShape shape) {

                float modifier = ThickSoulSyrupType.this.getFogDistanceModifier();

                if (modifier != 1f ) {

                    IClientFluidTypeExtensions.super.modifyFogRender(camera, mode, renderDistance, partialTick, nearDistance, farDistance, shape);

                } else {
                    IClientFluidTypeExtensions.super.modifyFogRender(camera, mode, renderDistance, partialTick, nearDistance, farDistance, shape);
                }
            }

            @Nullable
            @Override
            public ResourceLocation getOverlayTexture() {return null;}
        });
    }


    @Override
    protected int getTintColor(FluidStack stack) { return NO_TINT; }
    @Override
    protected int getTintColor(FluidState state, BlockAndTintGetter getter, BlockPos pos) { return NO_TINT; }
    @Nullable
    @Override
    protected Vector3f getCustomFogColor() { return null; }
    @Override
    protected float getFogDistanceModifier() { return 1.0f; }
}

