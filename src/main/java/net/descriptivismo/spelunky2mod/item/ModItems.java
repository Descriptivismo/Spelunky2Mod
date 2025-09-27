package net.descriptivismo.spelunky2mod.item;

import net.descriptivismo.spelunky2mod.Spelunky2Mod;
import net.descriptivismo.spelunky2mod.block.entity.ModEntities;
import net.descriptivismo.spelunky2mod.item.custom.BombItem;
import net.descriptivismo.spelunky2mod.item.custom.ResourceItem;
import net.descriptivismo.spelunky2mod.item.custom.RopeItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Spelunky2Mod.MODID);

    public static final RegistryObject<Item> GOLD_KEY = ITEMS.register("gold_key",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SKELETON_KEY = ITEMS.register("skeleton_key",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> UDJAT_EYE = ITEMS.register("udjat_eye",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SNAKE_SPAWN_EGG = ITEMS.register("snake_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.SNAKE, 0x9dc157, 0xf2f282,
                    new Item.Properties()));

    public static final RegistryObject<Item> BOMB = ITEMS.register("bomb",
            () -> new BombItem(new Item.Properties()));
    public static final RegistryObject<Item> ROPE = ITEMS.register("rope",
            () -> new RopeItem(new Item.Properties()));

    public static final RegistryObject<Item> BOMB_BAG = ITEMS.register("bomb_bag",
            () -> new ResourceItem(new Item.Properties()));
    public static final RegistryObject<Item> BOMB_BOX = ITEMS.register("bomb_box",
            () -> new ResourceItem(new Item.Properties()));
    public static final RegistryObject<Item> ROPE_PILE = ITEMS.register("rope_pile",
            () -> new ResourceItem(new Item.Properties()));

    public static final RegistryObject<Item> PASTE = ITEMS.register("paste",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
