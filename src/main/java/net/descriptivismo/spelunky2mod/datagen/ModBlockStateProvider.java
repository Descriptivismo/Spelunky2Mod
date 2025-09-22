package net.descriptivismo.spelunky2mod.datagen;

import net.descriptivismo.spelunky2mod.Spelunky2Mod;
import net.descriptivismo.spelunky2mod.block.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Spelunky2Mod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        cubeBottomTopWithItem(ModBlocks.DWELLING_ROCK);
        cubeBottomTopWithItem(ModBlocks.DWELLING_WOOD);
        cubeAllWithItem(ModBlocks.BONE_BLOCK);
        cubeDirectionalWithItem(ModBlocks.ARROW_TRAP);
    }

    private ResourceLocation extend(ResourceLocation rl, String suffix) {
        return ResourceLocation.fromNamespaceAndPath(rl.getNamespace(), rl.getPath() + suffix);
    }

    private void cubeDirectionalWithItem(RegistryObject<Block> blockRegistryObject)
    {
        ResourceLocation key = ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get());
        ResourceLocation baseName = ResourceLocation.fromNamespaceAndPath(key.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + key.getPath());
        ModelFile model = models().cube(
                key.getPath(),
                extend(baseName, "_bottom"),
                extend(baseName, "_top"),
                extend(baseName, "_front"),
                extend(baseName, "_back"),
                extend(baseName, "_right"),
                extend(baseName, "_left")
        ).texture("particle", extend(baseName, "_top"));

        directionalBlock(blockRegistryObject.get(), model);
        itemModels().getBuilder(key.getPath()).parent(model);
    }

    public void directionalBlock(Block block, Function<BlockState, ModelFile> modelFunc, int angleOffset) {
        getVariantBuilder(block)
                .forAllStates(state -> {
                    Direction dir = state.getValue(BlockStateProperties.FACING);
                    return ConfiguredModel.builder()
                            .modelFile(modelFunc.apply(state))
                            .rotationX(dir == Direction.DOWN ? 90 : dir == Direction.UP ? 270 : 0)
                            .rotationY(dir.getAxis().isVertical() ? 0 : (((int) dir.toYRot()) + angleOffset) % 360)
                            .build();
                });
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
