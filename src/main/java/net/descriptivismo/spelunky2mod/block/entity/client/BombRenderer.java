package net.descriptivismo.spelunky2mod.block.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.descriptivismo.spelunky2mod.Spelunky2Mod;
import net.descriptivismo.spelunky2mod.block.entity.custom.BombEntity;
import net.descriptivismo.spelunky2mod.block.entity.custom.SnakeEntity;
import net.minecraft.client.model.ShulkerBulletModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.ShulkerBullet;

public class BombRenderer extends EntityRenderer<BombEntity> {

    private final BombModel<BombEntity> model;

    public BombRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new BombModel<>(pContext.bakeLayer(ModModelLayers.BOMB_LAYER));
    }

    @Override
    public ResourceLocation getTextureLocation(BombEntity pEntity) {
        return ResourceLocation.fromNamespaceAndPath(Spelunky2Mod.MODID, "textures/entity/bomb_3d.png");
    }

    public void render(BombEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();
        pPoseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
        pPoseStack.translate(0.0F, -3.0F, 0.0F);
        pPoseStack.scale(2F, 2F, 2F);
        this.model.setupAnim(pEntity, 0.0F, 0.0F, pEntity.tickCount + pPartialTicks, 0, 0);
        VertexConsumer vertexconsumer = pBuffer.getBuffer(this.model.renderType(getTextureLocation(pEntity)));
        this.model.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pPoseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
}
