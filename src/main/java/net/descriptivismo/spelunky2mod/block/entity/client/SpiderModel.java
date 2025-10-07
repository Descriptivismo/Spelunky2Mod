package net.descriptivismo.spelunky2mod.block.entity.client;// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.descriptivismo.spelunky2mod.block.entity.animations.ModAnimationDefinitions;
import net.descriptivismo.spelunky2mod.block.entity.custom.SpiderEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class SpiderModel<T extends Entity> extends HierarchicalModel<T> {

	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart leg;
	private final ModelPart leg2;
	private final ModelPart leg3;
	private final ModelPart leg4;

	public SpiderModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body = this.root.getChild("body");
		this.leg = this.root.getChild("leg");
		this.leg2 = this.root.getChild("leg2");
		this.leg3 = this.root.getChild("leg3");
		this.leg4 = this.root.getChild("leg4");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 21.5F, -1.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -5.0F, -1.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(-2.0F, -3.0F, -2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 0).addBox(1.0F, -3.0F, -2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.5F, 0.0F));

        PartDefinition leg = root.addOrReplaceChild("leg", CubeListBuilder.create().texOffs(0, 11).addBox(-3.0F, -0.5F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -1.0F, 0.0F));

        PartDefinition bone = leg.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition legtop_r1 = bone.addOrReplaceChild("legtop_r1", CubeListBuilder.create().texOffs(0, 7).addBox(-3.0F, -1.0F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition leg2 = root.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(4, 11).addBox(-3.0F, -0.5F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -1.0F, 2.0F));

        PartDefinition bone2 = leg2.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition legtop_r2 = bone2.addOrReplaceChild("legtop_r2", CubeListBuilder.create().texOffs(8, 7).addBox(-3.0F, -1.0F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition leg3 = root.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(8, 11).addBox(-3.0F, -0.5F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -1.0F, 2.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition bone3 = leg3.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition legtop_r3 = bone3.addOrReplaceChild("legtop_r3", CubeListBuilder.create().texOffs(0, 9).addBox(-3.0F, -1.0F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition leg4 = root.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(12, 11).addBox(-3.0F, -0.5F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -1.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition bone4 = leg4.addOrReplaceChild("bone4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition legtop_r4 = bone4.addOrReplaceChild("legtop_r4", CubeListBuilder.create().texOffs(8, 9).addBox(-3.0F, -1.0F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.2618F));

        return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.animate(((SpiderEntity)entity).idleAnimationState, ModAnimationDefinitions.SPIDER_IDLE, ageInTicks, 1f);
        this.animate(((SpiderEntity)entity).perchedAnimationState, ModAnimationDefinitions.SPIDER_PERCHED, ageInTicks, 1f);
        this.animate(((SpiderEntity)entity).fallAnimationState, ModAnimationDefinitions.SPIDER_FALL, ageInTicks, 1f);
        this.animate(((SpiderEntity)entity).jumpAnimationState, ModAnimationDefinitions.SPIDER_JUMP, ageInTicks, 1f);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

    public ModelPart root() {
        return root;
    }
}