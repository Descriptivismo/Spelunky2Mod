package net.descriptivismo.spelunky2mod.block.custom;

import net.descriptivismo.spelunky2mod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;

public class UdjatChestBlock extends FallingBlock {


    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    public UdjatChestBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, Boolean.valueOf(false)));
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, OPEN);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

        if (pState.getValue(OPEN))
        {
            return InteractionResult.FAIL;
        }

        ItemStack handItem = pPlayer.getItemInHand(pHand);

        if (handItem.is(ModItems.GOLD_KEY.get()))
        {
            dropUdjatEye(pState, pLevel, pPos);
            handItem.shrink(1);
            return InteractionResult.SUCCESS;
        }

        if (handItem.is(ModItems.SKELETON_KEY.get()))
        {
            dropUdjatEye(pState, pLevel, pPos);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.SUCCESS;
    }

    private void dropUdjatEye(BlockState pState, Level pLevel, BlockPos pPos)
    {
        pLevel.setBlock(pPos, pState.setValue(OPEN, Boolean.valueOf(true)), 1 | 2);
        // play sound
        popResource(pLevel, pPos, new ItemStack(ModItems.UDJAT_EYE.get(), 1));
    }
}
