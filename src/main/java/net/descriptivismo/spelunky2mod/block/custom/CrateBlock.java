package net.descriptivismo.spelunky2mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CrateBlock extends FallingBlock {

    protected static final VoxelShape SHAPE = Shapes.or(
            Block.box(2.0D, 2.0D, 2.0D, 14.0D, 12.0D, 14.0D),
            Block.box(1.0D, 12.0D, 1.0D, 15.0D, 14.0D, 15.0D),
            Block.box(1.0D, 0.0D, 1.0D, 15.0D, 2.0D, 15.0D));

    public CrateBlock(Properties pProperties) {
        super(pProperties);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }
}
