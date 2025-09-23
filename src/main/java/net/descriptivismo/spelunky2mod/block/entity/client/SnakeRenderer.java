package net.descriptivismo.spelunky2mod.block.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.descriptivismo.spelunky2mod.Spelunky2Mod;
import net.descriptivismo.spelunky2mod.block.entity.custom.SnakeEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SnakeRenderer extends MobRenderer<SnakeEntity, SnakeModel<SnakeEntity>> {
    public SnakeRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new SnakeModel<>(pContext.bakeLayer(ModModelLayers.SNAKE_LAYER)), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(SnakeEntity pEntity) {
        return ResourceLocation.fromNamespaceAndPath(Spelunky2Mod.MODID, "textures/entity/snake.png");
    }
}
