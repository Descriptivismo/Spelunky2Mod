package net.descriptivismo.spelunky2mod.block.custom;

import net.descriptivismo.spelunky2mod.block.entity.ItemPickupBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ItemPickupBlock extends BaseEntityBlock {

    protected static final VoxelShape SHAPE = Shapes.or(
            Block.box(1.0D, 0.0D, 1.0D, 14.0D, 1.0D, 14.0D));

    public ItemPickupBlock(Properties pProperties)
    {
        super(pProperties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ItemPickupBlockEntity(pPos, pState);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pState.getBlock() != pNewState.getBlock())
        {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof ItemPickupBlockEntity)
            {
                ((ItemPickupBlockEntity)blockEntity).drops();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide())
        {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if (entity instanceof ItemPickupBlockEntity)
            {
                ItemPickupBlockEntity itemPickup = (ItemPickupBlockEntity) entity;
                itemPickup.onUse(pState, pLevel, pPos, pPlayer, pHand, pHit);
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pNeighborBlock, BlockPos pNeighborPos, boolean pMovedByPiston) {

        if (pLevel.isEmptyBlock(pNeighborPos) && pNeighborPos.getY() == pPos.getY() - 1)
        {
            pLevel.destroyBlock(pPos, true);
        }

        DebugPackets.sendNeighborsUpdatePacket(pLevel, pPos);
    }
}
