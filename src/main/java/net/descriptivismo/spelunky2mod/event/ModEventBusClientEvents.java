package net.descriptivismo.spelunky2mod.event;

import net.descriptivismo.spelunky2mod.Spelunky2Mod;
import net.descriptivismo.spelunky2mod.block.entity.ModBlockEntities;
import net.descriptivismo.spelunky2mod.block.entity.client.BombModel;
import net.descriptivismo.spelunky2mod.block.entity.client.ModModelLayers;
import net.descriptivismo.spelunky2mod.block.entity.client.SnakeModel;
import net.descriptivismo.spelunky2mod.block.entity.renderer.ItemPickupBlockEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Spelunky2Mod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents
{
    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerBlockEntityRenderer(ModBlockEntities.ITEM_PICKUP_BE.get(), ItemPickupBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(ModModelLayers.SNAKE_LAYER, SnakeModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.BOMB_LAYER, BombModel::createBodyLayer);
    }
}
