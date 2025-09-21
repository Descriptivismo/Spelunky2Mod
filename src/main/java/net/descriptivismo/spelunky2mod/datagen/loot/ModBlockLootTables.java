package net.descriptivismo.spelunky2mod.datagen.loot;

import net.descriptivismo.spelunky2mod.block.ModBlocks;
import net.descriptivismo.spelunky2mod.item.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {


    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.add(ModBlocks.DWELLING_ROCK.get(),
                block -> createDwellingRockDrops(ModBlocks.DWELLING_ROCK.get()));
        this.add(ModBlocks.DWELLING_WOOD.get(),
                block -> createSilkTouchOnlyTable(ModBlocks.DWELLING_WOOD.get()));
        this.add(ModBlocks.BONE_BLOCK.get(),
                block -> createSpecialItemDrops(ModBlocks.BONE_BLOCK.get(), Items.SKELETON_SKULL, ModItems.SKELETON_KEY.get()));
    }

    protected LootTable.Builder createDwellingRockDrops(Block pBlock) {
        return createSilkTouchDispatchTable(pBlock,
                LootItem.lootTableItem(Items.GOLD_NUGGET)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(-10.0F, 5.0F)))
                        .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)));
    }

    protected LootTable.Builder createSpecialItemDrops(Block pBlock, Item baseItem, Item specialItem)
    {
        return createSilkTouchDispatchTable(pBlock,
                LootItem.lootTableItem(baseItem)
                        .when(LootItemRandomChanceCondition.randomChance(0.5F)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(HAS_NO_SILK_TOUCH).add(LootItem.lootTableItem(specialItem)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))

                );
    }

    @Override
    protected Iterable<Block> getKnownBlocks()
    {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
