package net.descriptivismo.spelunky2mod.event;

import net.descriptivismo.spelunky2mod.Spelunky2Mod;
import net.descriptivismo.spelunky2mod.block.entity.ModEntities;
import net.descriptivismo.spelunky2mod.block.entity.client.ModModelLayers;
import net.descriptivismo.spelunky2mod.block.entity.client.SnakeModel;
import net.descriptivismo.spelunky2mod.block.entity.custom.SnakeEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Spelunky2Mod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event)
    {
        event.put(ModEntities.SNAKE.get(), SnakeEntity.createAttributes().build());
    }

}
