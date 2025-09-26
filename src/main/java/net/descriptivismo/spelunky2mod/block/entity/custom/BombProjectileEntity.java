package net.descriptivismo.spelunky2mod.block.entity.custom;

import net.descriptivismo.spelunky2mod.block.entity.ModEntities;
import net.descriptivismo.spelunky2mod.item.ModItems;
import net.descriptivismo.spelunky2mod.sound.ModSounds;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class BombProjectileEntity extends ThrowableItemProjectile {

    public BombProjectileEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public BombProjectileEntity(Level pLevel) {
        super(ModEntities.BOMB_PROJECTILE.get(), pLevel);
    }

    public BombProjectileEntity(Level pLevel, LivingEntity livingEntity) {
        super(ModEntities.BOMB_PROJECTILE.get(), livingEntity, pLevel);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.BOMB.get();
    }

    @Override
    protected float getGravity() {
        return 0.06f;
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {

        if (!this.level().isClientSide())
        {
            this.level().broadcastEntityEvent(this, ((byte) 3));


            BombEntity bomb = new BombEntity(ModEntities.BOMB.get(), this.level());

            Vec3 pos = pResult.getLocation();
            Direction dir = pResult.getDirection();
            if (dir.getAxis().isHorizontal())
            {
                pos = pos.add(dir.getStepX() / 4d, 0.0d, dir.getStepZ() / 4d);
            }

            bomb.setPos(pos);
            bomb.setDeltaMovement(0, this.getDeltaMovement().y, 0);

            this.level().addFreshEntity(bomb);

            this.level().playSeededSound(null, pos.x, pos.y, pos.z, ModSounds.FUSE.get(), SoundSource.BLOCKS,
                    1f, 1f, random.nextInt());

            kill();
        }
    }
}
