package net.descriptivismo.spelunky2mod.block.entity.client;

import net.descriptivismo.spelunky2mod.Spelunky2Mod;
import net.descriptivismo.spelunky2mod.block.entity.custom.SnakeEntity;
import net.descriptivismo.spelunky2mod.block.entity.custom.SpiderEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SpiderRenderer extends MobRenderer<SpiderEntity, SpiderModel<SpiderEntity>> {
    public SpiderRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new SpiderModel<>(pContext.bakeLayer(ModModelLayers.SPIDER_LAYER)), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(SpiderEntity pEntity) {
        return ResourceLocation.fromNamespaceAndPath(Spelunky2Mod.MODID, "textures/entity/spider.png");
    }
}
