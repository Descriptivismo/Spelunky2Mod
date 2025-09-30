package net.descriptivismo.spelunky2mod.block.custom;

import net.descriptivismo.spelunky2mod.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class SpikesBlock extends Block {

    public SpikesBlock(Properties pProperties) {
        super(pProperties);
    }

    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        pLevel.scheduleTick(pPos, this, 1);
    }

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom)
    {
        if (pLevel.isClientSide()) return;

        List<Entity> entities = pLevel.getEntitiesOfClass(Entity.class,
                AABB.ofSize(pPos.getCenter(), 1d, 1d, 1d), EntitySelector.NO_SPECTATORS);

        for (Entity entity : entities)
        {
            if (!(entity instanceof LivingEntity)) return;
            if (entity.getDeltaMovement().y < -0.1f && !((LivingEntity) entity).onClimbable()
                && entity.position().y > pPos.getY() + 0.5f && !entity.isInvulnerable())
            {
                entity.hurt(entity.damageSources().stalagmite(), 10);
                pLevel.playSeededSound(null, pPos.getX()+ 0.5f, pPos.getY() + 0.5f, pPos.getZ() + 0.5f,
                        ModSounds.IMPALE.get(), SoundSource.BLOCKS, 1f, 1f, pRandom.nextInt());
            }
        }

        pLevel.scheduleTick(pPos, this, 1);
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pNeighborBlock, BlockPos pNeighborPos, boolean pMovedByPiston) {
        super.neighborChanged(pState, pLevel, pPos, pNeighborBlock, pNeighborPos, pMovedByPiston);

        if (pNeighborPos.equals(pPos.below()) && pLevel.getBlockState(pNeighborPos).isAir())
        {
            pLevel.destroyBlock(pPos, false);
        }
    }
}
