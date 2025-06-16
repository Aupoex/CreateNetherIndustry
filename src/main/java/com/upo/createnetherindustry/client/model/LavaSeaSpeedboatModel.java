package com.upo.createnetherindustry.client.model;// Made with Blockbench 4.12.4

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class LavaSeaSpeedboatModel<T extends Entity> extends EntityModel<T> {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("createnetherindustry", "lava_speedboat"), "main");
	private final ModelPart bb_main;

	public LavaSeaSpeedboatModel(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 34).addBox(-3.0F, -14.0F, -13.0F, 6.0F, 5.0F, 27.0F, new CubeDeformation(0.001F))
		.texOffs(58, 66).addBox(-9.0F, -17.0F, 13.0F, 18.0F, 9.0F, 6.0F, new CubeDeformation(-0.001F))
		.texOffs(0, 66).addBox(-8.0F, -9.0F, -13.0F, 2.0F, 4.0F, 27.0F, new CubeDeformation(0.0F))
		.texOffs(66, 34).addBox(6.0F, -9.0F, -13.0F, 2.0F, 4.0F, 27.0F, new CubeDeformation(0.0F))
		.texOffs(74, 13).addBox(-8.0F, -16.0F, 7.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(74, 20).addBox(7.0F, -16.0F, 7.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(74, 0).addBox(-6.0F, -2.0F, -3.0F, 12.0F, 9.0F, 4.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(0.0F, -7.0F, 17.0F, -0.6981F, 0.0F, 0.0F));

		PartDefinition cube_r2 = bb_main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -2.0992F, -7.9608F, 12.0F, 9.0F, 25.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -6.9008F, 5.9608F, -3.1416F, 0.0F, 3.1416F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}
}