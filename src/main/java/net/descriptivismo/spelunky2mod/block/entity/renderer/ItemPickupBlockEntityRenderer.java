package net.descriptivismo.spelunky2mod.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.descriptivismo.spelunky2mod.block.entity.ItemPickupBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;

public class ItemPickupBlockEntityRenderer implements BlockEntityRenderer<ItemPickupBlockEntity> {

    public ItemPickupBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {

    }

    @Override
    public void render(ItemPickupBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack itemStack = pBlockEntity.getRenderStack();

        pPoseStack.pushPose();

        pPoseStack.translate(0.5f, 0.0f, 0.5f);
        pPoseStack.scale(0.75f, 0.75f, 0.75f);

        pPoseStack.rotateAround(Axis.YP.rotationDegrees(
                pBlockEntity.getBlockState().getValue(BlockStateProperties.ROTATION_16) / 16.0f * 360),
                0.0f, 0.0f, 0.0f);
        pPoseStack.rotateAround(Axis.XP.rotationDegrees(270), 0.0f, 0.0f, 0.0f);

        itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED,
                getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()),
                OverlayTexture.NO_OVERLAY, pPoseStack, pBuffer, pBlockEntity.getLevel(), 1);
        pPoseStack.popPose();
    }

    private int getLightLevel(Level level, BlockPos pos)
    {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}
