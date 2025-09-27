package net.descriptivismo.spelunky2mod.item.custom;

import net.descriptivismo.spelunky2mod.block.entity.custom.BombProjectileEntity;
import net.descriptivismo.spelunky2mod.item.ModItems;
import net.descriptivismo.spelunky2mod.sound.ModSounds;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BombItem extends Item {
    public BombItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), ModSounds.TOSS.get(),
                SoundSource.NEUTRAL, 1F, 1);
        if (!pLevel.isClientSide) {
            BombProjectileEntity bomb = new BombProjectileEntity(pLevel, pPlayer);
            bomb.setItem(itemStack);
            bomb.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.0F, 1.0F);

            if (pPlayer.getInventory().contains(new ItemStack(ModItems.PASTE.get())))
            {
                bomb.setPasteBomb();
            }

            pLevel.addFreshEntity(bomb);
        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        if (!pPlayer.getAbilities().instabuild) {
            itemStack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
    }
}
