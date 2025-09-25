package net.descriptivismo.spelunky2mod.item;

import net.descriptivismo.spelunky2mod.Spelunky2Mod;
import net.descriptivismo.spelunky2mod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Spelunky2Mod.MODID);

    public static final RegistryObject<CreativeModeTab> SPELUNKY_ITEMS_TAB = CREATIVE_MODE_TABS.register("spelunky_items_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.GOLD_KEY.get()))
                    .title(Component.translatable("creativetab.spelunky_items_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.SKELETON_KEY.get());
                        output.accept(ModItems.GOLD_KEY.get());
                        output.accept(ModItems.UDJAT_EYE.get());
                        output.accept(ModItems.BOMB.get());
                    })
                    .build());

    public static final RegistryObject<CreativeModeTab> SPELUNKY_BLOCKS_TAB = CREATIVE_MODE_TABS.register("spelunky_blocks_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModBlocks.DWELLING_ROCK.get()))
                    .title(Component.translatable("creativetab.spelunky_blocks_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.ITEM_PICKUP.get());
                        output.accept(ModBlocks.DWELLING_ROCK.get());
                        output.accept(ModBlocks.DWELLING_WOOD.get());
                        output.accept(ModBlocks.BONE_BLOCK.get());
                        output.accept(ModBlocks.ARROW_TRAP.get());
                        output.accept(ModBlocks.CRATE.get());
                        output.accept(ModBlocks.GOLD_BAR.get());
                        output.accept(ModBlocks.UDJAT_CHEST.get());
                    })
                    .build());

    public static final RegistryObject<CreativeModeTab> SPELUNKY_CREATURES_TAB = CREATIVE_MODE_TABS.register("spelunky_creatures_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.SNAKE_SPAWN_EGG.get()))
                    .title(Component.translatable("creativetab.spelunky_creatures_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.SNAKE_SPAWN_EGG.get());
                    })
                    .build());

    public static void register(IEventBus eventBus)
    {
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
