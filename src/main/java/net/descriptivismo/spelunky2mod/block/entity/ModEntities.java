package net.descriptivismo.spelunky2mod.block.entity;

import net.descriptivismo.spelunky2mod.Spelunky2Mod;
import net.descriptivismo.spelunky2mod.block.entity.custom.BombEntity;
import net.descriptivismo.spelunky2mod.block.entity.custom.SnakeEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Spelunky2Mod.MODID);

    public static final RegistryObject<EntityType<SnakeEntity>> SNAKE =
            ENTITY_TYPES.register("snake", () -> EntityType.Builder.of(SnakeEntity::new, MobCategory.MONSTER)
                    .sized(0.75f, 0.75f).build("snake"));

    public static final RegistryObject<EntityType<BombEntity>> BOMB =
            ENTITY_TYPES.register("bomb", () -> EntityType.Builder.of(BombEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("bomb"));

    public static void register(IEventBus eventBus)
    {
        ENTITY_TYPES.register(eventBus);
    }

}
