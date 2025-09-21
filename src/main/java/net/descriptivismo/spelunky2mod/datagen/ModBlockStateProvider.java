package net.descriptivismo.spelunky2mod.datagen;

import net.descriptivismo.spelunky2mod.Spelunky2Mod;
import net.descriptivismo.spelunky2mod.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Spelunky2Mod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        cubeBottomTopWithItem(ModBlocks.DWELLING_ROCK);
        cubeBottomTopWithItem(ModBlocks.DWELLING_WOOD);
        cubeAllWithItem(ModBlocks.BONE_BLOCK);
    }

    private ResourceLocation extend(ResourceLocation rl, String suffix) {
        return ResourceLocation.fromNamespaceAndPath(rl.getNamespace(), rl.getPath() + suffix);
    }

    private void cubeAllWithItem(RegistryObject<Block> blockRegistryObject)
    {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void cubeBottomTopWithItem(RegistryObject<Block> blockRegistryObject)
    {
        ResourceLocation key = ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get());
        ResourceLocation baseName = ResourceLocation.fromNamespaceAndPath(key.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + key.getPath());
        simpleBlockWithItem(blockRegistryObject.get(),
                models().cubeBottomTop(
                        key.getPath(),
                        extend(baseName, "_side"),
                        extend(baseName, "_bottom"),
                        extend(baseName, "_top")));
    }
}
