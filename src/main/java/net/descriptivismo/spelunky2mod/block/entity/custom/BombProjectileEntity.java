package net.descriptivismo.spelunky2mod.block.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class BombProjectileEntity extends ThrowableItemProjectile {

    public BombProjectileEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

//    public BombProjectileEntity(Level pLevel) {
//        super(, pLevel);
//    }
//
//    public BombProjectileEntity(Level pLevel, LivingEntity livingEntity) {
//        super(, livingEntity, pLevel);
//    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {

        if (!this.level().isClientSide())
        {
            this.level().broadcastEntityEvent(this, ((byte) 3));
            //this.level().addFreshEntity()
        }

        super.onHitBlock(pResult);
    }
}
