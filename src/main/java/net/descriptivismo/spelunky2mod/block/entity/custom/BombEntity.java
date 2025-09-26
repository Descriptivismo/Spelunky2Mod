package net.descriptivismo.spelunky2mod.block.entity.custom;

import net.descriptivismo.spelunky2mod.sound.ModSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BombEntity extends Entity {

    public final AnimationState countdownAnimationState = new AnimationState();

    private static final EntityDataAccessor<Integer> COUNTDOWN =
            SynchedEntityData.defineId(BombEntity.class, EntityDataSerializers.INT);

    private static final int countdownLength = 50;

    public BombEntity(EntityType pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {

        this.entityData.define(COUNTDOWN, countdownLength);

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

        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
        }

        this.move(MoverType.SELF, this.getDeltaMovement());

        int currCountdown = this.entityData.get(COUNTDOWN);
        if (currCountdown <= 0) {
            explode();
        }
        else {
            this.entityData.set(COUNTDOWN, currCountdown - 1);
        }
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
