package net.descriptivismo.spelunky2mod.block.custom;

import net.descriptivismo.spelunky2mod.block.ModBlocks;
import net.descriptivismo.spelunky2mod.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RopeBlock extends Block {

    protected static final VoxelShape SHAPE = Shapes.or(
            Block.box(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D));

    public static final IntegerProperty RANK = BlockStateProperties.AGE_7;
    public static final BooleanProperty PROPAGATE_UP = BlockStateProperties.UP;

    public RopeBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(RANK, 0).setValue(PROPAGATE_UP, true));
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState();
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(RANK, PROPAGATE_UP);
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pNeighborBlock, BlockPos pNeighborPos, boolean pMovedByPiston) {
        super.neighborChanged(pState, pLevel, pPos, pNeighborBlock, pNeighborPos, pMovedByPiston);

        if (pState.getValue(RANK) < 7 && pNeighborPos.equals(pPos.above())
            && !pLevel.getBlockState(pNeighborPos).is(ModBlocks.ROPE_BLOCK.get()))
        {
            pLevel.destroyBlock(pPos, false);
        }
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        super.onPlace(pState, pLevel, pPos, pOldState, pMovedByPiston);

        if (pState.getValue(RANK) >= 7)
        {
            pLevel.playSound(null, pPos, ModSounds.ROPE_ATTACH.get(), SoundSource.BLOCKS);
        }

        if (pState.getValue(RANK) < 7)
        {
            if (pState.getValue(PROPAGATE_UP))
            {
                if (pLevel.getBlockState(pPos.above()).isAir())
                {
                    // place rope with rank + 1 above
                    pLevel.setBlock(pPos.above(),
                            stateDefinition.any().setValue(RANK, pState.getValue(RANK) + 1)
                                    .setValue(PROPAGATE_UP, true), 1 | 2);
                }
                else
                {
                    // propagate down
                    propagateDown(pState, pLevel, pPos, 7);

                    pLevel.setBlock(pPos,
                            stateDefinition.any().setValue(RANK, 7), 1 | 2);
                }
            }
            else if (pState.getValue(RANK) > 0)
            {
                propagateDown(pState, pLevel, pPos, pState.getValue(RANK));
            }
        }
        else if (!pState.getValue(PROPAGATE_UP))
        {
            propagateDown(pState, pLevel, pPos, pState.getValue(RANK));
        }
    }

    public void propagateDown(BlockState pState, Level pLevel, BlockPos pPos, int rank)
    {
        BlockState belowBlock = pLevel.getBlockState(pPos.below());
        if (belowBlock.isAir() || belowBlock.is(ModBlocks.ROPE_BLOCK.get()))
        {
            // add a rope with rank - 1 and have it propagate
            pLevel.setBlock(pPos.below(),
                    stateDefinition.any().setValue(RANK, rank - 1)
                            .setValue(PROPAGATE_UP, false), 1 | 2);
        }
//        else if ()
//        {
//            // change rope to rank - 1 and have it propagate
//            RopeBlock belowRope = (RopeBlock)belowBlock.getBlock();
//            belowBlock.setValue(RANK, rank - 1);
//            belowRope.propagateDown(belowBlock, pLevel, pPos.below(), rank - 1);
//        }
    }
}