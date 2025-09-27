package net.descriptivismo.spelunky2mod.item.custom;

import net.descriptivismo.spelunky2mod.item.ModItems;
import net.descriptivismo.spelunky2mod.sound.ModSounds;
import net.minecraft.commands.CommandFunction;
import net.minecraft.commands.Commands;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.common.Mod;

public class ResourceItem extends Item {

    public ResourceItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);

        ItemStack resourcesToGive;
        if (!pLevel.isClientSide) {

            if (itemStack.is(ModItems.BOMB_BAG.get()))
                resourcesToGive = new ItemStack(ModItems.BOMB.get(), 3);
            else if (itemStack.is(ModItems.BOMB_BOX.get()))
                resourcesToGive = new ItemStack(ModItems.BOMB.get(), 12);
            else if (itemStack.is(ModItems.ROPE_PILE.get()))
                resourcesToGive = new ItemStack(ModItems.ROPE.get(), 3);
            else
                resourcesToGive = new ItemStack(Items.COAL, 1);

            if (!pPlayer.getInventory().add(resourcesToGive))
            {
                pPlayer.drop(resourcesToGive, false);
            }
        }

        pLevel.playSound(null, pPlayer.position().x, pPlayer.position().y, pPlayer.position().z,
                ModSounds.RESOURCE_GET.get(), SoundSource.PLAYERS, 0.6f, 1f);
        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        if (!pPlayer.getAbilities().instabuild) {
            itemStack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
    }

}
