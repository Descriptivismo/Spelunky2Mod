package net.descriptivismo.spelunky2mod.block.entity.client;// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.descriptivismo.spelunky2mod.block.entity.animations.ModAnimationDefinitions;
import net.descriptivismo.spelunky2mod.block.entity.custom.BombEntity;
import net.descriptivismo.spelunky2mod.block.entity.custom.SnakeEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class BombModel<T extends Entity> extends HierarchicalModel<T> {

	private final ModelPart root;
	private final ModelPart bomb;

	public BombModel(ModelPart root) {
		this.root = root.getChild("root");
		this.bomb = this.root.getChild("bomb");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 8).addBox(-0.5F, -5.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 8).addBox(-1.0F, -4.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition bomb = root.addOrReplaceChild("bomb", CubeListBuilder.create().texOffs(16, 0).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 16);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        //this.animateWalk(ModAnimationDefinitions.BOMB_COUNTDOWN, 1.0f, 1.0f, 1.0f, 1.0f);
        //((BombEntity) entity).countdownAnimationState.start(0);
        this.animate(((BombEntity) entity).countdownAnimationState, ModAnimationDefinitions.BOMB_COUNTDOWN, ageInTicks, 1f);
        //System.out.println(((BombEntity) entity).countdownAnimationState.isStarted());
        //System.out.println(((BombEntity) entity).countdownAnimationState.getAccumulatedTime());
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

    public ModelPart root() {
        return root;
    }
}