package net.descriptivismo.spelunky2mod.block;

import net.descriptivismo.spelunky2mod.Spelunky2Mod;
import net.descriptivismo.spelunky2mod.block.custom.*;
import net.descriptivismo.spelunky2mod.item.ModItems;
import net.descriptivismo.spelunky2mod.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Spelunky2Mod.MODID);

    public static final RegistryObject<Block> DWELLING_ROCK = registerBlock("dwelling_rock",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(1.0F, 6.0F)
            ));
    public static final RegistryObject<Block> UDJAT_CHEST = registerBlock("udjat_chest",
            () -> new UdjatChestBlock(BlockBehaviour.Properties.copy(Blocks.GLASS)
                    .strength(3600000.0F, 3600000.0F)
                    .noLootTable()
                    .noOcclusion()
            ));
    public static final RegistryObject<Block> DWELLING_WOOD = registerBlock("dwelling_wood",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)
            ));
    public static final RegistryObject<Block> BONE_BLOCK = registerBlock("bone_block",
            () -> new BoneBlock(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK)
                    .strength(0.01f, 1.0f)
                    .sound(ModSounds.BONE_BLOCK_SOUNDS)
            ));
    public static final RegistryObject<Block> ARROW_TRAP = registerBlock("arrow_trap",
            () -> new ArrowTrapBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
            ));
    public static final RegistryObject<Block> CRATE = registerBlock("crate",
            () -> new CrateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)
                    .strength(0.01f,1.0f)
                    .sound(ModSounds.CRATE_SOUNDS)
                    .noOcclusion()
            ));
    public static final RegistryObject<Block> ITEM_PICKUP = registerBlock("item_pickup",
            () -> new ItemPickupBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .noOcclusion()
                    .noLootTable()
                    .noCollission()
                    .strength(-1.0f, 3600000.0f)
                    .sound(ModSounds.NO_BREAK_SOUNDS)
            ));
    public static final RegistryObject<Block> GOLD_BAR = registerBlock("gold_bar",
            () -> new TreasureBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK)
                    .strength(3600000.0f, 1.0f)
                    .noOcclusion()
                    .noCollission()
                    .sound(ModSounds.NO_BREAK_SOUNDS)
            ));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block)
    {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block)
    {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }

}
