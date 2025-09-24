package net.descriptivismo.spelunky2mod.block.entity.custom;

import net.descriptivismo.spelunky2mod.block.entity.animations.ModAnimationDefinitions;
import net.descriptivismo.spelunky2mod.sound.ModSounds;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.predicates.DamageSourceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

import java.util.List;

public class SnakeEntity extends Monster {

    public SnakeEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(SnakeEntity.class, EntityDataSerializers.BOOLEAN);

    private static final float attackRange = 1f;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private int attackAnimationTimeout = 0;
    private Vec3 prevPos;

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide)
        {
            setUpAnimationStates();
        }
    }

    private void setUpAnimationStates()
    {
        if (!isAttacking())
            this.walkAnimationState.startIfStopped(this.tickCount);

        if (this.isAttacking() && attackAnimationTimeout <= 0)
        {
            setAttacking(false);
        } else
        {
            --this.attackAnimationTimeout;
        }


    }

    @Override
    public void playerTouch(Player pPlayer) {
        super.playerTouch(pPlayer);

        //if (pPlayer.distanceTo(this) < attackRange) {
        if (pPlayer.getBoundingBox().inflate(0.1f).intersects(this.getBoundingBox())) {

            //if (!isAttacking())
            {
                boolean playerHurt = pPlayer.hurt(damageSources().mobAttack(this),
                        (float)getAttribute(Attributes.ATTACK_DAMAGE).getValue());
                if (playerHurt) {
                    level().playSeededSound(null, position().x, position().y, position().z,
                            ModSounds.SNAKE_ATTACK.get(), SoundSource.HOSTILE, 1f, 1f, 0);

                }
            }

            setAttacking(true);
        }
    }

//    @Override
//    protected void updateWalkAnimation(float pPartialTick) {
//        float f;
//        if (this.getPose() == Pose.STANDING) {
//            f = Math.min(pPartialTick * 6f, 1f);
//        } else {
//            f = 0;
//        }
//
//        this.walkAnimation.update(f, 0.2f);
//    }

    public void setAttacking(boolean attacking)
    {
        if (attacking && !this.entityData.get(ATTACKING))
        {
            attackAnimationTimeout = 10;
            attackAnimationState.start(this.tickCount);
            walkAnimationState.stop();

        }
        this.entityData.set(ATTACKING, attacking);

    }

    public boolean isAttacking()
    {
        return this.entityData.get(ATTACKING);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        this.entityData.define(ATTACKING, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new WaterAvoidingRandomStrollGoal(this, 1));

        //this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, false));
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Monster.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 4)
                .add(Attributes.ATTACK_DAMAGE, 2)
                .add(Attributes.MOVEMENT_SPEED, 0.125d)
                .add(Attributes.FOLLOW_RANGE, 8)
                .add(Attributes.ATTACK_KNOCKBACK, 1)
                .add(Attributes.ATTACK_SPEED, 1)
                ;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return ModSounds.SNAKE_DIE.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.SNAKE_DIE.get();
    }

    public boolean shouldDropExperience() {
        return false;
    }

    protected boolean shouldDropLoot() {
        return false;
    }
}
