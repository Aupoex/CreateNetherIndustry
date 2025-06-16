package com.upo.createnetherindustry.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.upo.createnetherindustry.CreateNetherIndustry;
import com.upo.createnetherindustry.client.model.LavaSeaSpeedboatModel;
import com.upo.createnetherindustry.entity.LavaSpeedboatEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class LavaSpeedboatRenderer extends EntityRenderer<LavaSpeedboatEntity> {

    private static final ResourceLocation TEXTURE_LOCATION =
            ResourceLocation.fromNamespaceAndPath(CreateNetherIndustry.MODID, "textures/entity/lava_speedboat.png");

    private final LavaSeaSpeedboatModel<LavaSpeedboatEntity> model;

    public LavaSpeedboatRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.8F;
        this.model = new LavaSeaSpeedboatModel<>(context.bakeLayer(LavaSeaSpeedboatModel.LAYER_LOCATION));
    }

    @Override
    public void render(LavaSpeedboatEntity entity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack,
                       @NotNull MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        poseStack.translate(0.0D, 1.5D, 0.0D);

        poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));

        poseStack.mulPose(Axis.YP.rotationDegrees(entityYaw));

        float hurtTime = (float)entity.getHurtTime() - partialTicks;
        float damage = entity.getDamage() - partialTicks;
        if (damage < 0.0F) {
            damage = 0.0F;
        }
        if (hurtTime > 0.0F) {
            poseStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(hurtTime) * hurtTime * damage / 10.0F * (float)entity.getHurtDir()));
        }

        float bubbleTime = entity.getBubbleAngle(partialTicks);
        if (!Mth.equal(bubbleTime, 0.0F)) {
            poseStack.mulPose(Axis.XP.rotationDegrees(entity.getBubbleAngle(partialTicks)));
        }

        VertexConsumer vertexconsumer = buffer.getBuffer(this.model.renderType(this.getTextureLocation(entity)));

        this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, -1);

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @NotNull
    @Override
    public ResourceLocation getTextureLocation(@NotNull LavaSpeedboatEntity entity) {
        return TEXTURE_LOCATION;
    }
}
