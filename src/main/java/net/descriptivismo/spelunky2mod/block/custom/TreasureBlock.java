package net.descriptivismo.spelunky2mod.block.custom;

import net.descriptivismo.spelunky2mod.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TreasureBlock extends FallingBlock {
    public TreasureBlock(Properties pProperties) {
        super(pProperties);
    }

    protected static final VoxelShape SHAPE = Shapes.or(
            Block.box(4.0D, 0.0D, 4.0D, 12.0D, 6.0D, 12.0D));

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        pLevel.scheduleTick(pPos, this, 1);
    }

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom)
    {
        List<Player> collidingPlayers = pLevel.getEntitiesOfClass(Player.class,
                SHAPE.bounds().move(pPos), EntitySelector.NO_SPECTATORS);
        if (!collidingPlayers.isEmpty())
        {
            Inventory inventory = collidingPlayers.get(0).getInventory();
            ItemStack itemStack = new ItemStack(Items.GOLD_NUGGET, 3);
            inventory.add(itemStack);
            pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                    ModSounds.GOLD_COLLECT.get(), SoundSource.BLOCKS, 1f, 1f, 0);
            pLevel.destroyBlock(pPos, false);
        }

        pLevel.scheduleTick(pPos, this, 1);
    }
}
