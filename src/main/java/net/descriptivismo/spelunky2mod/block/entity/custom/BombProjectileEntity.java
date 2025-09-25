package net.descriptivismo.spelunky2mod.block.entity.custom;

import net.descriptivismo.spelunky2mod.block.entity.ModEntities;
import net.descriptivismo.spelunky2mod.item.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

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
            bomb.setPos(pResult.getLocation());

            this.level().addFreshEntity(bomb);


            kill();
        }
    }
}
