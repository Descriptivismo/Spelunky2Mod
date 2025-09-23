package net.descriptivismo.spelunky2mod.block.entity;

import net.descriptivismo.spelunky2mod.Spelunky2Mod;
import net.descriptivismo.spelunky2mod.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Spelunky2Mod.MODID);

    public static final RegistryObject<BlockEntityType<ItemPickupBlockEntity>> ITEM_PICKUP_BE =
            BLOCK_ENTITIES.register("item_pickup_be",
                    () -> BlockEntityType.Builder.of(ItemPickupBlockEntity::new,
                            ModBlocks.ITEM_PICKUP.get()).build(null));

    public static void register(IEventBus eventBus)
    {
        BLOCK_ENTITIES.register(eventBus);
    }

}
