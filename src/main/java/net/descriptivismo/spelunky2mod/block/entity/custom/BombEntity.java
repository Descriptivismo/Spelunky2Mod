package net.descriptivismo.spelunky2mod.block.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidType;

import java.util.List;

public class BombEntity extends Entity {

    public final AnimationState countdownAnimationState = new AnimationState();

    private static final EntityDataAccessor<Integer> COUNTDOWN =
            SynchedEntityData.defineId(BombEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> PASTE =
            SynchedEntityData.defineId(BombEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> EXPLODING =
            SynchedEntityData.defineId(BombEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> EXPLODING_NEXT_TICK =
            SynchedEntityData.defineId(BombEntity.class, EntityDataSerializers.BOOLEAN);

    private static final int countdownLength = 50;

    public BombEntity(EntityType pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {

        this.entityData.define(COUNTDOWN, countdownLength);
        this.entityData.define(PASTE, false);
        this.entityData.define(EXPLODING, false);
        this.entityData.define(EXPLODING_NEXT_TICK, false);

    }

    public boolean isPasteBomb()
    {
        return this.entityData.get(PASTE);
    }

    public void setPasteBomb()
    {
        this.entityData.set(PASTE, true);
    }

    @Override
    public boolean ignoreExplosion() {

        if (!this.entityData.get(EXPLODING) && !this.entityData.get(EXPLODING_NEXT_TICK))
        {
            this.entityData.set(EXPLODING_NEXT_TICK, true);
        }

        return false;
    }

    private void explode()
    {
        level().explode(this, position().x, position().y, position().z, 3.0f,
                Level.ExplosionInteraction.TNT);
        kill();
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide)
        {
            setUpAnimationStates();
        }

        if (!entityData.get(PASTE) && !this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
        }

        this.move(MoverType.SELF, this.getDeltaMovement());

        int currCountdown = this.entityData.get(COUNTDOWN);
        if (currCountdown <= 0) {
            //explode();
            this.entityData.set(EXPLODING, true);
        }
        else {
            this.entityData.set(COUNTDOWN, currCountdown - 1);
        }

        if (isOnFire())
            this.entityData.set(EXPLODING, true);

        if (!level().isClientSide && this.entityData.get(EXPLODING))
            explode();

        if (this.entityData.get(EXPLODING_NEXT_TICK)) {
            this.entityData.set(EXPLODING, true);
            this.entityData.set(EXPLODING_NEXT_TICK, false);
        }
    }

    @Override
    public boolean isPushedByFluid(FluidType type) {
        return !this.entityData.get(PASTE);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }

    private void setUpAnimationStates() {
        countdownAnimationState.startIfStopped(0);

        //this.tickCount++;
        //countdownAnimationState.updateTime(this.tickCount, 1);
        //System.out.println(countdownAnimationState.getAccumulatedTime());
    }

}
