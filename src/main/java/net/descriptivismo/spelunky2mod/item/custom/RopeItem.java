package net.descriptivismo.spelunky2mod.item.custom;

import net.descriptivismo.spelunky2mod.block.ModBlocks;
import net.descriptivismo.spelunky2mod.block.entity.custom.BombProjectileEntity;
import net.descriptivismo.spelunky2mod.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class RopeItem extends Item {
    public RopeItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {

        Level pLevel = pContext.getLevel();
        Player pPlayer = pContext.getPlayer();
        ItemStack itemStack = pContext.getItemInHand();

        Direction dir = pContext.getClickedFace();
        BlockPos pos = pContext.getClickedPos().offset(dir.getStepX(), dir.getStepY(), dir.getStepZ());
        if (!pLevel.getBlockState(pos).isAir())
            return InteractionResult.FAIL;

        pLevel.playSeededSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), ModSounds.ROPE_TOSS.get(),
                SoundSource.NEUTRAL, 1F, 1F, pLevel.random.nextLong());
        if (!pLevel.isClientSide) {

            if (pContext.isSecondaryUseActive())
            {
                pLevel.setBlock(pos, ModBlocks.ROPE_BLOCK.get().getStateDefinition().any()
                        .setValue(BlockStateProperties.AGE_7, 7)
                        .setValue(BlockStateProperties.UP, false), 1 | 2);
            }
            else
            {
                pLevel.setBlock(pos, ModBlocks.ROPE_BLOCK.get().getStateDefinition().any()
                        .setValue(BlockStateProperties.AGE_7, 0)
                        .setValue(BlockStateProperties.UP, true), 1 | 2);
            }
        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        if (!pPlayer.getAbilities().instabuild) {
            itemStack.shrink(1);
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }
}
