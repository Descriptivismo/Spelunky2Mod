package net.descriptivismo.spelunky2mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BoneBlock extends Block {


    public BoneBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pNeighborBlock, BlockPos pNeighborPos, boolean pMovedByPiston) {

        if (pLevel.isEmptyBlock(pNeighborPos) && pNeighborPos.getY() == pPos.getY() - 1)
        {
            pLevel.destroyBlock(pPos, true);
        }

        DebugPackets.sendNeighborsUpdatePacket(pLevel, pPos);
    }
}
