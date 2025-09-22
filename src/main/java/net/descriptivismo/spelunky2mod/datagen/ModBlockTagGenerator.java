package net.descriptivismo.spelunky2mod.datagen;

import net.descriptivismo.spelunky2mod.Spelunky2Mod;
import net.descriptivismo.spelunky2mod.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {

    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Spelunky2Mod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.DWELLING_ROCK.get())
                .add(ModBlocks.ARROW_TRAP.get());
        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.DWELLING_WOOD.get());
    }
}
