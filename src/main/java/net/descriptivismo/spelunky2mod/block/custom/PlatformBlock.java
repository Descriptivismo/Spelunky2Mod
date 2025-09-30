package net.descriptivismo.spelunky2mod.block.custom;

import net.descriptivismo.spelunky2mod.block.ModBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class PlatformBlock extends Block {

    protected static final VoxelShape SHAPE = Shapes.or(
            Block.box(0.0D, 7.0D, 0.0D, 16.0D, 16.0D, 16.0D));

    public PlatformBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if (pContext.isAbove(Shapes.block(), pPos, true) && !pContext.isDescending())
        {
            return SHAPE;
        }
        else
        {
            return Shapes.empty();
        }
    }

    
}
